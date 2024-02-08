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

    @Test
    void rotateVectorAroundZAxis() {
        Vector3 vectorToRotate = new Vector3(1, 0, 0);
        Vector3 rotatedVector = vectorToRotate.rotateVectorAroundAxis(new Vector3(0, 0, 1), Math.PI / 2);

        assertEquals(new Vector3(0, 1, 0), rotatedVector);
    }

    @Test
    void rotateZAxisVectorAroundZAxis() {
        Vector3 vectorToRotate = new Vector3(0, 0, 1);
        Vector3 rotatedVector = vectorToRotate.rotateVectorAroundAxis(new Vector3(0, 0, 1), Math.random() * Math.PI * 2);

        assertEquals(new Vector3(0, 0, 1), rotatedVector);
    }

    @Test
    void rotate30DegreesAroundZAxis() {
        Vector3 vectorToRotate = new Vector3(1, 0, 0);
        Vector3 rotatedVector = vectorToRotate.rotateVectorAroundAxis(new Vector3(0, 0, 1), Math.toRadians(30));

        assertEquals(new Vector3(Math.sqrt(3) / 2, 0.5, 0), rotatedVector);
    }

    @Test
    void rotate30DegreesAroundYAxis() {
        Vector3 vectorToRotate = new Vector3(1, 0, 0);
        Vector3 rotatedVector = vectorToRotate.rotateVectorAroundAxis(new Vector3(0, 1, 0), Math.toRadians(30));

        assertEquals(new Vector3(Math.cos(Math.toRadians(30)), 0, -Math.sin(Math.toRadians(30))), rotatedVector);
    }
}