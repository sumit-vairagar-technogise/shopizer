#!/bin/bash
echo "=== Java Version Lock Verification ==="
echo ""
echo "1. Current Java version:"
java -version 2>&1 | head -1
echo ""
echo "2. Maven Java version:"
mvn -version | grep "Java version"
echo ""
echo "3. Locked to: Java 17 (enforced by maven-enforcer-plugin)"
echo ""
echo "4. Test build:"
mvn clean compile -q 2>&1 | tail -3
echo ""
echo "✅ If BUILD SUCCESS → Java 17 is active"
echo "❌ If BUILD FAILURE → Wrong Java version detected"
