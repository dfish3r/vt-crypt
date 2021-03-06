/*
  $Id$

  Copyright (C) 2003-2013 Virginia Tech.
  All rights reserved.

  SEE LICENSE FOR MORE INFORMATION

  Author:  Middleware Services
  Email:   middleware@vt.edu
  Version: $Revision$
  Updated: $Date$
*/
package edu.vt.middleware.crypt.symmetric;

/**
 * Provider of symmetric encryption/decryption operations using DES cipher.
 *
 * @author  Middleware Services
 * @version  $Revision$
 */

public class DES extends SymmetricAlgorithm
{

  /** Algorithm name. */
  public static final String ALGORITHM = "DES";

  /** Available key lengths in bits. */
  public static final int[] KEY_LENGTHS = new int[] {64};


  /**
   * Creates a default DES symmetric encryption algorithm using CBC mode and
   * PKCS5 padding.
   */
  public DES()
  {
    this(DEFAULT_MODE, DEFAULT_PADDING);
  }


  /**
   * Creates a default DES symmetric encryption algorithm using the given mode
   * and padding style.
   *
   * @param  mode  Cipher mode name.
   * @param  padding  Cipher padding style name.
   */
  public DES(final String mode, final String padding)
  {
    super(ALGORITHM, mode, padding);
  }


  /** {@inheritDoc} */
  public int[] getAllowedKeyLengths()
  {
    return KEY_LENGTHS;
  }
}
