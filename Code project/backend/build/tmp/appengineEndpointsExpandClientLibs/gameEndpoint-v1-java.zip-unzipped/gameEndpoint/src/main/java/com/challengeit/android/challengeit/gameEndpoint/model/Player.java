/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * This code was generated by https://code.google.com/p/google-apis-client-generator/
 * (build: 2015-01-14 17:53:03 UTC)
 * on 2015-03-23 at 06:05:03 UTC 
 * Modify at your own risk.
 */

package com.challengeit.android.challengeit.gameEndpoint.model;

/**
 * Model definition for Player.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the gameEndpoint. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class Player extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long gameId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private com.google.api.client.util.DateTime lastUpdate;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<Response> myResponses;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String playername;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer score;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String username;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getGameId() {
    return gameId;
  }

  /**
   * @param gameId gameId or {@code null} for none
   */
  public Player setGameId(java.lang.Long gameId) {
    this.gameId = gameId;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public com.google.api.client.util.DateTime getLastUpdate() {
    return lastUpdate;
  }

  /**
   * @param lastUpdate lastUpdate or {@code null} for none
   */
  public Player setLastUpdate(com.google.api.client.util.DateTime lastUpdate) {
    this.lastUpdate = lastUpdate;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<Response> getMyResponses() {
    return myResponses;
  }

  /**
   * @param myResponses myResponses or {@code null} for none
   */
  public Player setMyResponses(java.util.List<Response> myResponses) {
    this.myResponses = myResponses;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getPlayername() {
    return playername;
  }

  /**
   * @param playername playername or {@code null} for none
   */
  public Player setPlayername(java.lang.String playername) {
    this.playername = playername;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getScore() {
    return score;
  }

  /**
   * @param score score or {@code null} for none
   */
  public Player setScore(java.lang.Integer score) {
    this.score = score;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getUsername() {
    return username;
  }

  /**
   * @param username username or {@code null} for none
   */
  public Player setUsername(java.lang.String username) {
    this.username = username;
    return this;
  }

  @Override
  public Player set(String fieldName, Object value) {
    return (Player) super.set(fieldName, value);
  }

  @Override
  public Player clone() {
    return (Player) super.clone();
  }

}
