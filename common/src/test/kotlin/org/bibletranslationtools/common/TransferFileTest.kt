package org.bibletranslationtools.common

import com.nhaarman.mockitokotlin2.doReturn
import org.bibletranslationtools.common.client.IFileTransferClient
import org.bibletranslationtools.common.usecases.TransferFile
import org.junit.Test
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Completable

class TransferFileTest {

    @Test
    fun testTransferComplete() {
        val client = mock<IFileTransferClient> {
            on { transfer() } doReturn Completable.complete()
        }
        val result = TransferFile(client).transfer().test()

        result.assertComplete()
        result.assertNoErrors()
    }

    @Test
    fun testTransferFailed() {
        val client = mock<IFileTransferClient> {
            on { transfer() } doReturn Completable.error(Exception("An error occurred"))
        }
        val result = TransferFile(client).transfer().test()

        result.assertError(Exception::class.java)
        result.assertErrorMessage("An error occurred")
        result.assertNotComplete()
    }
}
