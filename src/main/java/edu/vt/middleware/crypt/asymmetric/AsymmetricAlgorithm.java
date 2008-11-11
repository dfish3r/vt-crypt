/*
  $Id$

  Copyright (C) 2003-2008 Virginia Tech.
  All rights reserved.

  SEE LICENSE FOR MORE INFORMATION

  Author:  Middleware Services
  Email:   middleware@vt.edu
  Version: $Revision$
  Updated: $Date$
*/
package edu.vt.middleware.crypt.asymmetric;

import java.security.KeyPair;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.util.HashMap;
import java.util.Map;
import edu.vt.middleware.crypt.AbstractEncryptionAlgorithm;
import edu.vt.middleware.crypt.CryptException;
import edu.vt.middleware.crypt.CryptProvider;

/**
 * Provides asymmetric encryption and decryption operations using a
 * public/private key pair.
 *
 * @author  Middleware Services
 * @version  $Revision$
 */

public class AsymmetricAlgorithm extends AbstractEncryptionAlgorithm
{

  /** Mode used for encryption and decryption. */
  public static final String MODE = "NONE";

  /** Padding used for encryption and decryption. */
  public static final String PADDING = "OAEPPadding";

  /** Size of chunks in stream-based encryption. */
  private static final int CHUNK_SIZE = 2048;

  /** Map of digest algorithm names to classes. */
  private static final Map NAME_CLASS_MAP = new HashMap();


  /**
   * Class initializer.
   */
  static {
    NAME_CLASS_MAP.put("RSA", "edu.vt.middleware.crypt.asymmetric.RSA");
  }


  /**
   * Creates a new instance that uses a cipher of the given algorithm and the
   * default mode and padding.
   *
   * @param  cipherAlgorithm  Cipher algorithm name.
   */
  protected AsymmetricAlgorithm(final String cipherAlgorithm)
  {
    super(cipherAlgorithm, MODE, PADDING);
  }


  /**
   * Creates a new instance that uses a cipher of the given name.
   *
   * @param  name  Cipher algorithm name.
   *
   * @return  Asymmetric algorithm instance that implements the given cipher
   * algorithm.
   */
  public static AsymmetricAlgorithm newInstance(final String name)
  {
    final String className = (String) NAME_CLASS_MAP.get(name.toUpperCase());
    if (className != null) {
      try {
        return (AsymmetricAlgorithm) Class.forName(className).newInstance();
      } catch (Exception ex) {
        throw new IllegalArgumentException(ex.getMessage());
      }
    } else {
      // Search provider
      return new AsymmetricAlgorithm(name);
    }
  }


  /**
   * Generates a public/private key pair using the given cipher algorithm.
   *
   * @param  algorithm  Name of a cipher algorithm for which a suitable key pair
   * will be generated.
   * @param  bitLength  Size of each key in pair in bits.
   * @param  random  Source of randomness used for key generation.
   *
   * @return  Key pair that may be used for encryption/decryption on a cipher of
   * the given algorithm.
   *
   * @throws  CryptException  On key pair generation errors.
   */
  public static KeyPair generateKeys(
    final String algorithm,
    final int bitLength,
    final SecureRandom random)
    throws CryptException
  {
    if (random == null) {
      throw new CryptException("Source of random data cannot be null.");
    }
    return CryptProvider.getKeyPairGenerator(algorithm).generateKeyPair();
  }


  /**
   * Generates a public/private key pair of the default length suitable for the
   * cipher used for encryption on this instance.
   *
   * @return  Key pair that may be used for encryption/decryption on the cipher
   * used by this instance.
   *
   * @throws  CryptException  On key pair generation errors.
   */
  public KeyPair generateKeys()
    throws CryptException
  {
    return generateKeys(getDefaultKeyLength());
  }


  /**
   * Generates a public/private key pair of the given length suitable for the
   * cipher used for encryption on this instance.
   *
   * @param  bitLength  Size of each key in pair in bits.
   *
   * @return  Key pair that may be used for encryption/decryption on the cipher
   * used by this instance.
   *
   * @throws  CryptException  On key pair generation errors.
   */
  public KeyPair generateKeys(final int bitLength)
    throws CryptException
  {
    if (randomProvider != null) {
      return generateKeys(algorithm, bitLength, randomProvider);
    } else {
      return generateKeys(algorithm, bitLength, new SecureRandom());
    }
  }


  /**
   * Gets the default key length for this algorithm.
   *
   * @return  Default key length in bits.
   */
  public int getDefaultKeyLength()
  {
    throw new UnsupportedOperationException("Default key length is not known.");
  }


  /** {@inheritDoc} */
  protected AlgorithmParameterSpec getAlgorithmParameterSpec()
  {
    return null;
  }


  /** {@inheritDoc} */
  protected int getChunkSize()
  {
    return CHUNK_SIZE;
  }
}
