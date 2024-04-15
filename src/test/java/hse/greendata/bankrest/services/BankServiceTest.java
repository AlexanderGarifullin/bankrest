package hse.greendata.bankrest.services;

import hse.greendata.bankrest.models.Bank;
import hse.greendata.bankrest.repositories.BankRepository;
import hse.greendata.bankrest.util.exceptions.Bank.BankNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BankServiceTest {

    @Mock
    private BankRepository bankRepository;

    @InjectMocks
    private BankService bankService;
    @Test
    void testFindOne_WhenBankExists_ReturnBank() {
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

    @Test
    void testFindOne_WhenBankNotExists_ThrowException() {
        when(bankRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(BankNotFoundException.class, () -> {
            bankService.findOne(1);
        });
    }



}