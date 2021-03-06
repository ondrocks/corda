package net.corda.core.serialization.amqp.custom

import net.corda.core.serialization.amqp.CustomSerializer
import java.util.*

/**
 * A custom serializer for the [Currency] class, utilizing the currency code string representation.
 */
object CurrencySerializer : CustomSerializer.ToString<Currency>(Currency::class.java,
        withInheritance = false,
        maker = { Currency.getInstance(it) },
        unmaker = { it.currencyCode })
