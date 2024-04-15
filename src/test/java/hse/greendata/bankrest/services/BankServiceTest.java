package hse.greendata.bankrest.services;

import hse.greendata.bankrest.models.Bank;
import hse.greendata.bankrest.repositories.BankRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BankServiceTest {

    @Test
    void testFindOne() {
        BankRepository bankRepository = Mockito.mock(BankRepository.class);

        Bank bank = new Bank();
        bank.setId(1);
        bank.setName("Test Bank");

        when(bankRepository.findById(1)).thenReturn(Optional.of(bank));

        BankService bankService = new BankService(bankRepository);

        Bank result = bankService.findOne(1);

        assertEquals(bank.getId(), result.getId());
        assertEquals(bank.getName(), result.getName());

        verify(bankRepository).findById(1);
    }
}