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
 * on 2015-03-23 at 06:05:05 UTC 
 * Modify at your own risk.
 */

package com.challengeit.android.challengeit.userLocalEndpoint.model;

/**
 * Model definition for Game.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the userLocalEndpoint. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class Game extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<Challenge> challenges;

  static {
    // hack to force ProGuard to consider Challenge used, since otherwise it would be stripped out
    // see http://code.google.com/p/google-api-java-client/issues/detail?id=528
    com.google.api.client.util.Data.nullOf(Challenge.class);
  }

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private com.google.api.client.util.DateTime dateCreation;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String description;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String gameName;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer highestScore;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long id;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<Player> players;

  static {
    // hack to force ProGuard to consider Player used, since otherwise it would be stripped out
    // see http://code.google.com/p/google-api-java-client/issues/detail?id=528
    com.google.api.client.util.Data.nullOf(Player.class);
  }

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<Challenge> getChallenges() {
    return challenges;
  }

  /**
   * @param challenges challenges or {@code null} for none
   */
  public Game setChallenges(java.util.List<Challenge> challenges) {
    this.challenges = challenges;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public com.google.api.client.util.DateTime getDateCreation() {
    return dateCreation;
  }

  /**
   * @param dateCreation dateCreation or {@code null} for none
   */
  public Game setDateCreation(com.google.api.client.util.DateTime dateCreation) {
    this.dateCreation = dateCreation;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getDescription() {
    return description;
  }

  /**
   * @param description description or {@code null} for none
   */
  public Game setDescription(java.lang.String description) {
    this.description = description;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getGameName() {
    return gameName;
  }

  /**
   * @param gameName gameName or {@code null} for none
   */
  public Game setGameName(java.lang.String gameName) {
    this.gameName = gameName;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getHighestScore() {
    return highestScore;
  }

  /**
   * @param highestScore highestScore or {@code null} for none
   */
  public Game setHighestScore(java.lang.Integer highestScore) {
    this.highestScore = highestScore;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getId() {
    return id;
  }

  /**
   * @param id id or {@code null} for none
   */
  public Game setId(java.lang.Long id) {
    this.id = id;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<Player> getPlayers() {
    return players;
  }

  /**
   * @param players players or {@code null} for none
   */
  public Game setPlayers(java.util.List<Player> players) {
    this.players = players;
    return this;
  }

  @Override
  public Game set(String fieldName, Object value) {
    return (Game) super.set(fieldName, value);
  }

  @Override
  public Game clone() {
    return (Game) super.clone();
  }

}
