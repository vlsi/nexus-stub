package com.github.vlsi.nexusstub.staging.store

import jetbrains.exodus.database.TransientEntityStore
import jetbrains.exodus.database.TransientStoreSession
import kotlinx.dnq.session
import org.springframework.stereotype.Component
import org.springframework.transaction.TransactionDefinition
import org.springframework.transaction.support.AbstractPlatformTransactionManager
import org.springframework.transaction.support.DefaultTransactionStatus

@Component
class XodusTransactionManager(
    private val xodus: TransientEntityStore
) : AbstractPlatformTransactionManager() {
    override fun doBegin(transaction: Any, definition: TransactionDefinition) {
        if (definition.isReadOnly) {
            xodus.beginReadonlyTransaction()
        } else {
            xodus.beginSession();
        }
    }

    override fun doSuspend(transaction: Any) = xodus.suspendThreadSession()

    override fun doResume(transaction: Any?, suspendedResources: Any) {
        xodus.resumeSession(suspendedResources as TransientStoreSession)
    }

    override fun doGetTransaction() = xodus

    override fun doRollback(status: DefaultTransactionStatus) {
        xodus.session.abort()
    }

    override fun doCommit(status: DefaultTransactionStatus) {
        xodus.session.commit()
    }
}
