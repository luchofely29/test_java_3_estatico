package com.company;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * Provides static utility methods for common operations.
 *
 * <p>This class is not meant to be instantiated.</p>
 */
public final class CleanUtils {

  private static final Logger LOGGER =
      Logger.getLogger(CleanUtils.class.getName());

  /** Application version constant. */
  public static final String VERSION = "1.0";

  private CleanUtils() {
    // Utility class — prevent instantiation
  }

  /**
   * Returns the sum of two integers.
   *
   * @param firstValue the first operand
   * @param secondValue the second operand
   * @return the sum of firstValue and secondValue
   */
  public static int calculateSum(int firstValue, int secondValue) {
    return firstValue + secondValue;
  }

  /**
   * Reads the entire content of a file as a UTF-8 string.
   *
   * @param filePath the path to the file to read
   * @return the file content as a {@code String}
   * @throws IOException if an I/O error occurs reading the file
   * @throws NullPointerException if filePath is null
   */
  public static String readFileContent(Path filePath)
      throws IOException {
    Objects.requireNonNull(filePath, "filePath must not be null");
    return Files.readString(filePath, StandardCharsets.UTF_8);
  }

  /**
   * Writes a string to a file using UTF-8 encoding.
   *
   * @param filePath the destination path
   * @param content the content to write
   * @throws IOException if an I/O error occurs writing the file
   * @throws NullPointerException if filePath or content is null
   */
  public static void writeFileContent(Path filePath, String content)
      throws IOException {
    Objects.requireNonNull(filePath, "filePath must not be null");
    Objects.requireNonNull(content, "content must not be null");
    Files.writeString(filePath, content, StandardCharsets.UTF_8);
  }

  /**
   * Compares two strings in constant time to prevent timing attacks.
   *
   * @param first the first string to compare
   * @param second the second string to compare
   * @return {@code true} if the strings are equal, {@code false} otherwise
   * @throws NoSuchAlgorithmException if SHA-256 algorithm is unavailable
   * @throws NullPointerException if either argument is null
   */
  public static boolean secureCompare(String first, String second)
      throws NoSuchAlgorithmException {
    Objects.requireNonNull(first, "first must not be null");
    Objects.requireNonNull(second, "second must not be null");
    MessageDigest digest = MessageDigest.getInstance("SHA-256");
    byte[] firstHash = digest.digest(
        first.getBytes(StandardCharsets.UTF_8));
    digest.reset();
    byte[] secondHash = digest.digest(
        second.getBytes(StandardCharsets.UTF_8));
    return MessageDigest.isEqual(firstHash, secondHash);
  }

  /**
   * Retrieves the API key from the environment variable {@code APP_API_KEY}.
   *
   * @return the API key value
   * @throws IllegalStateException if the environment variable is not set
   */
  public static String getApiKey() {
    String apiKey = System.getenv("APP_API_KEY");
    if (apiKey == null || apiKey.isBlank()) {
      throw new IllegalStateException(
          "APP_API_KEY environment variable is not set.");
    }
    LOGGER.fine("API key retrieved successfully.");
    return apiKey;
  }
}
