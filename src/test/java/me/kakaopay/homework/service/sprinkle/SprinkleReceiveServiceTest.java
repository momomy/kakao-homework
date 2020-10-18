package me.kakaopay.homework.service.sprinkle;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import me.kakaopay.homework.entity.BalanceSprinkle;
import me.kakaopay.homework.entity.BalanceSprinkleTransaction;
import me.kakaopay.homework.service.sprinkle.exception.SprinkleAlreadyReceiveException;
import me.kakaopay.homework.service.sprinkle.exception.SprinkleExpiredException;
import me.kakaopay.homework.service.sprinkle.exception.SprinkleNotFoundException;
import me.kakaopay.homework.service.sprinkle.exception.SprinkleOwnerException;
import me.kakaopay.homework.service.sprinkle.vo.SprinkleReceiveRequestVo;
import me.kakaopay.homework.service.sprinkle.vo.SprinkleTransactionVo;
import me.kakaopay.homework.service.sprinkle.vo.SprinkleVo;

@RunWith(MockitoJUnitRunner.class)
public class SprinkleReceiveServiceTest {

    @Mock
    private SprinkleService sprinkleService;

    @Mock
    private SprinkleCacheManager sprinkleCacheManager;

    @Mock
    private SprinkleTransactionService sprinkleTransactionService;

    @InjectMocks
    private SprinkleReceiveService sprinkleReceiveService;

    private static final long OWNER_USER_ID = 0L;
    private static final long USER_ID = 1L;
    private static final long SPRINKLE_ID = 2L;

    private static final String ROOM_ID = "ROOM";
    private static final String TOKEN = "TOKEN";

    @Test
    public void receive() {
        SprinkleVo sprinkleVo = Mockito.mock(SprinkleVo.class);
        when(sprinkleVo.getRoomId()).thenReturn(ROOM_ID);
        when(sprinkleVo.getUserId()).thenReturn(OWNER_USER_ID);
        when(sprinkleVo.isExpired()).thenReturn(false);
        when(sprinkleVo.getId()).thenReturn(SPRINKLE_ID);
        when(sprinkleCacheManager.get(TOKEN)).thenReturn(Optional.of(sprinkleVo));

        when(sprinkleCacheManager.isAbleToPop(TOKEN)).thenReturn(true);

        when(sprinkleCacheManager.pop(TOKEN)).thenReturn(BigDecimal.TEN);

        BalanceSprinkle balanceSprinkle = Mockito.mock(BalanceSprinkle.class);
        when(balanceSprinkle.getTransactions()).thenReturn(Lists.newArrayList());
        when(sprinkleService.get(SPRINKLE_ID)).thenReturn(balanceSprinkle);

        SprinkleTransactionVo sprinkleTransactionVo = Mockito.mock(SprinkleTransactionVo.class);
        when(sprinkleTransactionService.insert(balanceSprinkle, USER_ID, BigDecimal.TEN)).thenReturn(
                sprinkleTransactionVo);

        SprinkleReceiveRequestVo vo = SprinkleReceiveRequestVo.of(USER_ID, ROOM_ID, TOKEN);
        assertThat(sprinkleReceiveService.receive(vo)).isEqualTo(sprinkleTransactionVo);
    }

    @Test(expected = SprinkleNotFoundException.class)
    public void receive_뿌리기를찾을수없음() {
        when(sprinkleCacheManager.get(TOKEN)).thenReturn(Optional.empty());

        SprinkleReceiveRequestVo vo = SprinkleReceiveRequestVo.of(USER_ID, ROOM_ID, TOKEN);
        sprinkleReceiveService.receive(vo);
        fail("");
    }

    @Test(expected = SprinkleNotFoundException.class)
    public void receive_방이틀림() {
        SprinkleVo sprinkleVo = Mockito.mock(SprinkleVo.class);
        when(sprinkleVo.getRoomId()).thenReturn("OTHER");
        when(sprinkleCacheManager.get(TOKEN)).thenReturn(Optional.of(sprinkleVo));

        SprinkleReceiveRequestVo vo = SprinkleReceiveRequestVo.of(USER_ID, ROOM_ID, TOKEN);
        sprinkleReceiveService.receive(vo);
        fail("");
    }

    @Test(expected = SprinkleExpiredException.class)
    public void receive_만료됨() {
        SprinkleVo sprinkleVo = Mockito.mock(SprinkleVo.class);
        when(sprinkleVo.getRoomId()).thenReturn(ROOM_ID);
        when(sprinkleVo.isExpired()).thenReturn(true);
        when(sprinkleCacheManager.get(TOKEN)).thenReturn(Optional.of(sprinkleVo));
        SprinkleReceiveRequestVo vo = SprinkleReceiveRequestVo.of(USER_ID, ROOM_ID, TOKEN);
        sprinkleReceiveService.receive(vo);
        fail("");
    }

    @Test(expected = SprinkleOwnerException.class)
    public void receive_동일한유저() {
        SprinkleVo sprinkleVo = Mockito.mock(SprinkleVo.class);
        when(sprinkleVo.getRoomId()).thenReturn(ROOM_ID);
        when(sprinkleVo.getUserId()).thenReturn(OWNER_USER_ID);
        when(sprinkleVo.isExpired()).thenReturn(false);
        when(sprinkleCacheManager.get(TOKEN)).thenReturn(Optional.of(sprinkleVo));

        SprinkleReceiveRequestVo vo = SprinkleReceiveRequestVo.of(OWNER_USER_ID, ROOM_ID, TOKEN);
        sprinkleReceiveService.receive(vo);
        fail("");
    }

    @Test(expected = SprinkleExpiredException.class)
    public void receive_남아있는분배금액없음() {
        SprinkleVo sprinkleVo = Mockito.mock(SprinkleVo.class);
        when(sprinkleVo.getRoomId()).thenReturn(ROOM_ID);
        when(sprinkleVo.getUserId()).thenReturn(OWNER_USER_ID);
        when(sprinkleVo.isExpired()).thenReturn(false);
        when(sprinkleCacheManager.get(TOKEN)).thenReturn(Optional.of(sprinkleVo));

        when(sprinkleCacheManager.isAbleToPop(TOKEN)).thenReturn(false);

        SprinkleReceiveRequestVo vo = SprinkleReceiveRequestVo.of(USER_ID, ROOM_ID, TOKEN);
        sprinkleReceiveService.receive(vo);
        fail("");
    }

    @Test(expected = SprinkleAlreadyReceiveException.class)
    public void receive_이미받은유저() {
        SprinkleVo sprinkleVo = Mockito.mock(SprinkleVo.class);
        when(sprinkleVo.getRoomId()).thenReturn(ROOM_ID);
        when(sprinkleVo.getUserId()).thenReturn(OWNER_USER_ID);
        when(sprinkleVo.isExpired()).thenReturn(false);
        when(sprinkleVo.getId()).thenReturn(SPRINKLE_ID);
        when(sprinkleCacheManager.get(TOKEN)).thenReturn(Optional.of(sprinkleVo));

        when(sprinkleCacheManager.isAbleToPop(TOKEN)).thenReturn(true);

        BalanceSprinkle balanceSprinkle = Mockito.mock(BalanceSprinkle.class);
        BalanceSprinkleTransaction sprinkleTransaction = Mockito.mock(BalanceSprinkleTransaction.class);
        when(sprinkleTransaction.getUserId()).thenReturn(USER_ID);
        when(balanceSprinkle.getTransactions()).thenReturn(Lists.newArrayList(sprinkleTransaction));
        when(sprinkleService.get(SPRINKLE_ID)).thenReturn(balanceSprinkle);

        SprinkleReceiveRequestVo vo = SprinkleReceiveRequestVo.of(USER_ID, ROOM_ID, TOKEN);
        sprinkleReceiveService.receive(vo);
        fail("");
    }
}