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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

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

        verify(bankRepository).findById(1);
    }

    @Test
    void testFindAll_WhenBanksExists_ReturnListOfBanks() {
        Bank bank = new Bank(1, "bank", "123456789");
        List<Bank> expectedBanks = List.of(bank);

        when(bankRepository.findAll()).thenReturn(expectedBanks);

        List<Bank> actualBanks = bankService.findAll();

        assertEquals(expectedBanks.size(), actualBanks.size());
        for (int i = 0; i < expectedBanks.size(); i++) {
            Bank bank1 = expectedBanks.get(i);
            Bank bank2 = actualBanks.get(i);
            assertEquals(bank1.getId(), bank2.getId());
            assertEquals(bank1.getName(), bank2.getName());
            assertEquals(bank1.getBik(), bank2.getBik());
        }

        verify(bankRepository, times(1)).findAll();
    }

    @Test
    void testFindOneByName_WhenBankExist_ReturnBank() {
        Bank bank = new Bank(1, "bank", "123456789");
        when(bankRepository.findByName(bank.getName())).thenReturn(Optional.of(bank));

        Optional<Bank> result = bankService.findOneByName(bank.getName());

        assert(result.isPresent());
        Bank b = result.orElse(null);
        assertEquals(bank.getId(),b.getId());
        assertEquals(bank.getName(), b.getName());
        assertEquals(bank.getBik(), b.getBik());
    }

    @Test
    void testFindOneByBik_WhenBankExist_ReturnBank() {
        Bank bank = new Bank(1, "bank", "123456789");
        when(bankRepository.findByBik(bank.getBik())).thenReturn(Optional.of(bank));

        Optional<Bank> result = bankService.findOneByBik(bank.getBik());

        assert(result.isPresent());
        Bank b = result.orElse(null);
        assertEquals(bank.getId(),b.getId());
        assertEquals(bank.getName(), b.getName());
        assertEquals(bank.getBik(), b.getBik());
    }

}