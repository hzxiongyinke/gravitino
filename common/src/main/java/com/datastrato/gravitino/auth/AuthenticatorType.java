/*
 * Copyright 2023 Datastrato Pvt Ltd.
 * This software is licensed under the Apache License version 2.
 */

package com.datastrato.gravitino.auth;

public enum AuthenticatorType {
  NONE,
  SIMPLE,
  OAUTH,
  KERBEROS
}
