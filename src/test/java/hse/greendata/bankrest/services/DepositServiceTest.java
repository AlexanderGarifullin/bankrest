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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
}