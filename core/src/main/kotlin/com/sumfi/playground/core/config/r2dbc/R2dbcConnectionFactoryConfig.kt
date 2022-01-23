package com.sumfi.playground.core.config.r2dbc

import com.github.jasync.r2dbc.mysql.JasyncConnectionFactory
import com.github.jasync.sql.db.mysql.pool.MySQLConnectionFactory
import com.github.jasync.sql.db.mysql.util.URLParser
import io.r2dbc.spi.ConnectionFactory
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.r2dbc.connection.R2dbcTransactionManager

@Configuration
class R2dbcConnectionFactoryConfig {
    @Bean
    @ConfigurationProperties("sumfi.application.datasource.master")
    fun masterDataSourceProperties(): DataSourceProperties {
        return DataSourceProperties()
    }

    @Bean
    @ConfigurationProperties("sumfi.application.datasource.slave")
    fun slaveDataSourceProperties(): DataSourceProperties {
        return DataSourceProperties()
    }

    @Bean
    fun masterConnectionFactory(masterDataSourceProperties: DataSourceProperties): ConnectionFactory {
        val properties = URLParser.parseOrDie(masterDataSourceProperties.url)

        return JasyncConnectionFactory(
            MySQLConnectionFactory(
            com.github.jasync.sql.db.Configuration(
                username = masterDataSourceProperties.username,
                password = masterDataSourceProperties.password,
                host = properties.host,
                port = properties.port,
                database = properties.database,
                charset = properties.charset,
                ssl = properties.ssl
            ))
        )
    }

    @Bean
    fun slaveConnectionFactory(slaveDataSourceProperties: DataSourceProperties): ConnectionFactory {
        val properties = URLParser.parseOrDie(slaveDataSourceProperties.url)

        return JasyncConnectionFactory(
            MySQLConnectionFactory(
                com.github.jasync.sql.db.Configuration(
                    username = slaveDataSourceProperties.username,
                    password = slaveDataSourceProperties.password,
                    host = properties.host,
                    port = properties.port,
                    database = properties.database,
                    charset = properties.charset,
                    ssl = properties.ssl
                ))
        )
    }


    @Bean
    fun masterTransactionManager(masterConnectionFactory: ConnectionFactory) = R2dbcTransactionManager(masterConnectionFactory)

    @Bean
    fun slaveTransactionManager(slaveConnectionFactory: ConnectionFactory) = R2dbcTransactionManager(slaveConnectionFactory)

}
