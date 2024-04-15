package hse.greendata.bankrest.services;

import hse.greendata.bankrest.models.Bank;
import hse.greendata.bankrest.repositories.BankRepository;
import hse.greendata.bankrest.util.exceptions.Bank.BankNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

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

        when(bankRepository.findAll(Sort.by("id"))).thenReturn(expectedBanks);

        List<Bank> actualBanks = bankService.findAll("id");

        assertEquals(expectedBanks.size(), actualBanks.size());
        for (int i = 0; i < expectedBanks.size(); i++) {
            Bank bank1 = expectedBanks.get(i);
            Bank bank2 = actualBanks.get(i);
            assertEquals(bank1.getId(), bank2.getId());
            assertEquals(bank1.getName(), bank2.getName());
            assertEquals(bank1.getBik(), bank2.getBik());
        }

        verify(bankRepository, times(1)).findAll(Sort.by("id"));
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

        verify(bankRepository, times(1)).findByName(bank.getName());
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

        verify(bankRepository, times(1)).findByBik(bank.getBik());
    }

    @Test
    void testFindOneById_WhenBankExist_ReturnBank() {
        Bank bank = new Bank(1, "bank", "123456789");
        when(bankRepository.findById(bank.getId())).thenReturn(Optional.of(bank));

        Optional<Bank> result = bankService.findOneById(bank.getId());

        assert(result.isPresent());
        Bank b = result.orElse(null);
        assertEquals(bank.getId(),b.getId());
        assertEquals(bank.getName(), b.getName());
        assertEquals(bank.getBik(), b.getBik());

        verify(bankRepository, times(1)).findById(bank.getId());
    }

    @Test
    void testSave() {
        Bank bank = new Bank(1, "bank", "123456789");

        bankService.save(bank);

        verify(bankRepository, times(1)).save(any(Bank.class));
    }

    @Test
    void testUpdate() {
        int id = 1;
        Bank bank = new Bank(2, "bank", "123456789");

        bankService.update(id, bank);

        verify(bankRepository, times(1)).save(bank);

        assertEquals(id, bank.getId());
    }

    @Test
    void testDelete() {
        int id = 1;

        bankService.delete(id);

        verify(bankRepository, times(1)).deleteById(id);
    }
}