package net.corda.core.crypto

import org.junit.Test
import kotlin.test.assertTrue

/**
 * Digital signature MetaData tests.
 */
class TransactionSignatureTest {

    val testBytes = "12345678901234567890123456789012".toByteArray()

    /** Valid sign and verify. */
    @Test
    fun `MetaData Full sign and verify`() {
        val keyPair = Crypto.generateKeyPair("ECDSA_SECP256K1_SHA256")

        // Create a MetaData object.
        val meta = MetaData(1, testBytes.sha256(), keyPair.public)

        // Sign the meta object.
        val transactionSignature: TransactionSignature = keyPair.private.sign(meta)

        // Check auto-verification.
        assertTrue(transactionSignature.verify())

        // Check manual verification.
        assertTrue(Crypto.doVerify(keyPair.public, testBytes.sha256(), transactionSignature))
    }

    /** Verification should fail; public key has changed. */
    @Test(expected = IllegalArgumentException::class)
    fun `MetaData Full failure public key has changed`() {
        val keyPair1 = Crypto.generateKeyPair("ECDSA_SECP256K1_SHA256")
        val keyPair2 = Crypto.generateKeyPair("ECDSA_SECP256K1_SHA256")
        val meta = MetaData(1, testBytes.sha256(), keyPair1.public)
        val transactionSignature = keyPair1.private.sign(meta)
        Crypto.doVerify(keyPair2.public, testBytes.sha256(), transactionSignature)
    }

    /** Verification should fail; corrupted metadata - clearData (merkle root) has changed. */
    @Test(expected = IllegalArgumentException::class)
    fun `MetaData Full failure clearData has changed`() {
        val keyPair = Crypto.generateKeyPair("ECDSA_SECP256K1_SHA256")
        val meta = MetaData(1, testBytes.sha256(), keyPair.public)
        val transactionSignature = keyPair.private.sign(meta)
        Crypto.doVerify(keyPair.public, (testBytes + testBytes).sha256(), transactionSignature)
    }
}
