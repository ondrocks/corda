package net.corda.core.crypto

import net.corda.core.serialization.CordaSerializable
import net.corda.core.serialization.serialize

@CordaSerializable
open class ExtraMetaData(val platformVersion: Int) {

    fun bytes() = this.serialize().bytes

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ExtraMetaData) return false
        return platformVersion == other.platformVersion
    }

    override fun hashCode(): Int {
        return platformVersion
    }

    override fun toString(): String {
        return "ExtraMetaData(platformVersion=$platformVersion)"
    }
}