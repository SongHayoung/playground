package com.sumfi.playground.core.config.r2dbc

import org.springframework.r2dbc.connection.lookup.AbstractRoutingConnectionFactory
import org.springframework.transaction.reactive.TransactionSynchronizationManager
import reactor.core.publisher.Mono

class SumfiConnectionRouterFactory(): AbstractRoutingConnectionFactory() {
    companion object {
        const val MASTER = "MASTER"
        const val SLAVE = "SLAVE"
    }
    override fun determineCurrentLookupKey(): Mono<Any> {
        return TransactionSynchronizationManager.forCurrentTransaction().map {
            if (it.isCurrentTransactionReadOnly) { SLAVE } else { MASTER }
        }
    }
}
