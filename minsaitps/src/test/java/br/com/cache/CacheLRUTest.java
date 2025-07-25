package br.com.cache;
import br.com.cache.Cache;
import br.com.cache.CacheLRU;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class CacheLRUTest
{

    private Cache<String, String> cache;

    @BeforeEach
    public void setUp() {
        // Suponha que o CacheLRU tenha um construtor com capacidade máxima
        this.cache = new CacheLRU<>(3);
    }

    @Test
    public void testPutAndGet() {
        cache.put("a", "valorA");
        cache.put("b", "valorB");

        assertEquals("valorA", cache.get("a"));
        assertEquals("valorB", cache.get("b"));
        assertNull(cache.get("c"));
    }

    @Test
    public void testRemove() {
        cache.put("x", "valorX");
        cache.put("y", "valorY");

        cache.remove("x");
        assertNull(cache.get("x"));
        assertEquals("valorY", cache.get("y"));
    }

    @Test
    public void testSize() {
        cache.put("1", "um");
        cache.put("2", "dois");
        cache.put("3", "três");

        assertEquals(3, cache.size());

        cache.remove("2");

        assertEquals(2, cache.size());
    }

    @Test
    public void testEvictionOrderLRU() {
        cache.put("A", "1");
        cache.put("B", "2");
        cache.put("C", "3");

        cache.get("A");

        cache.put("D", "4");

        assertNotNull(cache.get("A"));
        assertNull(cache.get("B"));
        assertNotNull(cache.get("C"));
        assertNotNull(cache.get("D"));
    }
}
