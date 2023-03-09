package utilities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Vector3Test {

    @Test
    void reflect() {
        Vector3 vectorToReflect = new Vector3(0, 1, 1);
        Vector3 normal = new Vector3(0, 1, 0);

        Vector3 reflectedVector = vectorToReflect.reflect(normal);

        assertEquals(new Vector3(0, 1, -1), reflectedVector);
    }
}