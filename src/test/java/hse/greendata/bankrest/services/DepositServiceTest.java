package hse.greendata.bankrest.services;

import hse.greendata.bankrest.models.Client;
import hse.greendata.bankrest.models.Deposit;
import hse.greendata.bankrest.repositories.DepositRepository;
import hse.greendata.bankrest.util.exceptions.Client.ClientNotFoundException;
import hse.greendata.bankrest.util.exceptions.Deposit.DepositNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepositServiceTest {
    @Mock
    private DepositRepository depositRepository;

    @InjectMocks
    private DepositService depositService;

    @Test
    void testFindOne_WhenDepositExists_ReturnDeposit() {
        Deposit deposit = new Deposit();
        deposit.setId(1);
        deposit.setBankId(1);
        deposit.setClientId(1);
        deposit.setInterestRate(1.4);
        deposit.setTermMonths(3);
        deposit.setOpeningDate(LocalDate.of(2020,2,2));

        when(depositRepository.findById(1)).thenReturn(Optional.of(deposit));

        Deposit result = depositService.findOne(1);

        assertEquals(deposit.getId(), result.getId());
        assertEquals(deposit.getBankId(), result.getBankId());
        assertEquals(deposit.getClientId(), result.getClientId());
        assertEquals(deposit.getOpeningDate(), result.getOpeningDate());
        assertEquals(deposit.getInterestRate(), result.getInterestRate());
        assertEquals(deposit.getTermMonths(), result.getTermMonths());

        verify(depositRepository).findById(1);
    }

    @Test
    void testFindOne_WhenDepositNotExists_ThrowException() {
        when(depositRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(DepositNotFoundException.class, () -> {
            depositService.findOne(1);
        });

        verify(depositRepository).findById(1);
    }

    @Test
    void testFindAll_WhenDepositsExist_ReturnListOfDeposits() {
        Deposit deposit = new Deposit(1, 1, 1 , LocalDate.of(2022,2,2),
                15.2, 4);
        List<Deposit> expectedDeposits = List.of(deposit);

        when(depositRepository.findAll()).thenReturn(expectedDeposits);

        List<Deposit> actualDeposits = depositService.findAll();

        assertEquals(expectedDeposits.size(), actualDeposits.size());
        for (int i = 0; i < expectedDeposits.size(); i++) {
            Deposit deposit1 = expectedDeposits.get(i);
            Deposit deposit2 = actualDeposits.get(i);
            assertEquals(deposit1.getId(), deposit2.getId());
            assertEquals(deposit1.getBankId(), deposit2.getBankId());
            assertEquals(deposit1.getClientId(), deposit2.getClientId());
            assertEquals(deposit1.getOpeningDate(), deposit2.getOpeningDate());
            assertEquals(deposit1.getInterestRate(), deposit2.getInterestRate());
            assertEquals(deposit1.getTermMonths(), deposit2.getTermMonths());
        }

        verify(depositRepository, times(1)).findAll();
    }

    @Test
    void testSave() {
        Deposit deposit = new Deposit(1, 1, 1 , LocalDate.of(2022,2,2),
                15.2, 4);
        depositService.save(deposit);

        verify(depositRepository, times(1)).save(any(Deposit.class));
    }

    @Test
    void testUpdate() {
        int id = 1;
        Deposit deposit = new Deposit(1, 1, 1 , LocalDate.of(2022,2,2),
                15.2, 4);
        depositService.update(id, deposit);

        verify(depositRepository, times(1)).save(deposit);

        assertEquals(id, deposit.getId());
    }

    @Test
    void testDelete() {
        int id = 1;

        depositService.delete(id);

        verify(depositRepository, times(1)).deleteById(id);
    }
}