package net.corda.core.crypto

import net.corda.core.serialization.CordaSerializable
import net.corda.core.serialization.serialize
import java.security.PublicKey

/**
 * Using a [MetaData] object a signer can add extra information on the transaction signature. It actually works as a
 * wrapper that contains the platform version used when signing, the Merkle root of the transaction and the signer's
 * [PublicKey]. MetaData can be extended to support a universal digital signature model enabling partial signatures and
 * attaching extra information, such as a user's timestamp or other application-specific fields.
 *
 * @param platformVersion DLT's version.
 * @param merkleRoot the Merkle root of the transaction.
 * @param publicKey the signer's public key.
 */
@CordaSerializable
open class MetaData(
        val platformVersion: Int,
        val merkleRoot: SecureHash,
        val publicKey: PublicKey) {

    fun bytes() = this.serialize().bytes

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is MetaData) return false
        return (platformVersion == other.platformVersion
                && merkleRoot == other.merkleRoot
                && publicKey == other.publicKey)
    }

    override fun hashCode(): Int {
        var result = platformVersion
        result = 31 * result + merkleRoot.hashCode()
        result = 31 * result + publicKey.hashCode()
        return result
    }
}

