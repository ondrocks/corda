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
 * @param merkleRoot the Merkle root of the transaction.
 * @param publicKey the signer's public key.
 * @param extraMetaData other meta data required, which usually is not included in the transaction itself.
 */
@CordaSerializable
class MetaData(val merkleRoot: SecureHash,
               val publicKey: PublicKey,
               val extraMetaData: ExtraMetaData) {

    fun bytes() = this.serialize().bytes

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is MetaData) return false
        return (extraMetaData == other.extraMetaData
                && merkleRoot == other.merkleRoot
                && publicKey == other.publicKey)
    }

    override fun hashCode(): Int {
        var result = extraMetaData.hashCode()
        result = 31 * result + merkleRoot.hashCode()
        result = 31 * result + publicKey.hashCode()
        return result
    }

    override fun toString(): String {
        return "MetaData(merkleRoot=$merkleRoot, publicKey=$publicKey, extraMetaData=$extraMetaData)"
    }

    fun extraMetaDataBytes() = extraMetaData.bytes()
}

