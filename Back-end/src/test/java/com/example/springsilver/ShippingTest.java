package com.example.springsilver;


import com.example.springsilver.models.Shipping;
import com.example.springsilver.models.exceptions.ShippingNotFoundException;
import com.example.springsilver.repository.ShippingRepository;
import com.example.springsilver.service.impl.ShippingServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;


import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ShippingTest {
    @Mock
    private ShippingRepository shippingRepository;

    @InjectMocks
    private ShippingServiceImpl shippingService;

    @Test
    public void testSave() {
        Shipping shipping = new Shipping();
        when(shippingRepository.save(shipping)).thenReturn(shipping);

        Shipping result = shippingService.save(shipping);

        assertNotNull(result);
        assertEquals(shipping, result);
        verify(shippingRepository).save(shipping);
    }

    @Test
    public void testFindAll() {
        Shipping shipping1 = new Shipping();
        Shipping shipping2 = new Shipping();
        List<Shipping> shippings = Arrays.asList(shipping1, shipping2);
        when(shippingRepository.findAll()).thenReturn(shippings);

        List<Shipping> result = shippingService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(shippingRepository).findAll();
    }

    @Test
    public void testFindById_Success() {
        Long id = 1L;
        Shipping shipping = new Shipping();
        when(shippingRepository.findById(id)).thenReturn(Optional.of(shipping));

        Optional<Shipping> result = shippingService.findById(id);

        assertTrue(result.isPresent());
        assertEquals(shipping, result.get());
        verify(shippingRepository).findById(id);
    }

    @Test
    public void testFindById_NotFound() {
        Long id = 1L;
        when(shippingRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Shipping> result = shippingService.findById(id);

        assertFalse(result.isPresent());
        verify(shippingRepository).findById(id);
    }

    @Test
    public void testFindByShippingDate_Success() {
        LocalDate shippingDate = LocalDate.now();
        Shipping shipping = new Shipping();
        when(shippingRepository.findByShipmentDate(shippingDate)).thenReturn(Optional.of(shipping));

        Optional<Shipping> result = shippingService.findByShippingDate(shippingDate);

        assertTrue(result.isPresent());
        assertEquals(shipping, result.get());
        verify(shippingRepository).findByShipmentDate(shippingDate);
    }

    @Test
    public void testFindByShippingDate_NotFound() {
        LocalDate shippingDate = LocalDate.now();
        when(shippingRepository.findByShipmentDate(shippingDate)).thenReturn(Optional.empty());

        Optional<Shipping> result = shippingService.findByShippingDate(shippingDate);

        assertFalse(result.isPresent());
        verify(shippingRepository).findByShipmentDate(shippingDate);
    }

    @Test
    public void testEdit_Success() {
        Long id = 1L;
        LocalDate shipmentDate = LocalDate.now();
        String address = "123 Street";
        Shipping shipping = new Shipping();
        when(shippingRepository.findById(id)).thenReturn(Optional.of(shipping));

        Optional<Shipping> result = shippingService.edit(id, shipmentDate, address);

        assertTrue(result.isPresent());
        assertEquals(shipmentDate, shipping.getShipmentDate());
        assertEquals(address, shipping.getAddress());
        verify(shippingRepository).findById(id);
        verify(shippingRepository).save(shipping);
    }

    @Test
    public void testEdit_NotFound() {
        Long id = 1L;
        LocalDate shipmentDate = LocalDate.now();
        String address = "123 Street";
        when(shippingRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ShippingNotFoundException.class, () -> shippingService.edit(id, shipmentDate, address));
        verify(shippingRepository).findById(id);
    }

    @Test
    public void testDeleteById() {
        Long id = 1L;
        doNothing().when(shippingRepository).deleteById(id);

        shippingService.deleteById(id);

        verify(shippingRepository).deleteById(id);
    }
}

