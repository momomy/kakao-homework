package me.kakaopay.homework.service.sprinkle;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SprinkleTokenGeneratorTest {

    private static final long USER_ID = 1L;

    @Mock
    private SprinkleCacheManager sprinkleCacheManager;

    @InjectMocks
    private SprinkleTokenGenerator sprinkleTokenGenerator;

    @Test
    public void generate() {

        assertThat(sprinkleTokenGenerator.generate(USER_ID, 3)).hasSize(3);
        assertThat(sprinkleTokenGenerator.generate(USER_ID, 3)).hasSize(3);
        assertThat(sprinkleTokenGenerator.generate(USER_ID, 3)).hasSize(3);
    }

    @Test(expected = IllegalStateException.class)
    public void generate_Token생성실패() {

        final String lower = "abcdefghijklmnopqrstuvwxyz";
        final String upper = lower.toUpperCase();
        final String number = "0123456789";

        final Set<String> token = new HashSet<>();
        for (int i = 0; i < lower.length(); i++) {
            token.add(String.valueOf(lower.charAt(i)));
        }

        for (int i = 0; i < upper.length(); i++) {
            token.add(String.valueOf(upper.charAt(i)));
        }

        for (int i = 0; i < number.length(); i++) {
            token.add(String.valueOf(number.charAt(i)));
        }
        when(sprinkleCacheManager.contain(any(String.class))).thenReturn(true);
        sprinkleTokenGenerator.generate(USER_ID, 1);
        fail("");
    }
}