package io.datatroops.dao

import io.datatroops.domain.models.TournamentTable
import slick.jdbc.PostgresProfile.api._

import java.sql.Timestamp
import java.util.UUID

class Tournaments(tag: Tag) extends Table[TournamentTable](tag, "tournament") {
  def id = column[UUID]("id", O.PrimaryKey)

  def name = column[Option[String]]("name")

  def description = column[Option[String]]("description")

  def enabled = column[Boolean]("enabled")

  def starttime = column[Timestamp]("starttime")

  def endtime = column[Timestamp]("endtime")

  def ownerId = column[UUID]("owner_id")

  def gameId = column[UUID]("game_id")

  def startingBalance = column[BigDecimal]("starting_balance")

  def lastModified = column[Timestamp]("last_modified")

  def groupId = column[Option[UUID]]("group_id")

  def disableGroupNotifications = column[Boolean]("disable_group_notifications")

  def groupMessage = column[Option[String]]("group_message")

  def disableNongroupNotifications = column[Boolean]("disable_nongroup_notifications")

  def nongroupMessage = column[String]("nongroup_message")

  def privateViewing = column[Boolean]("private_viewing")

  def * = (
    id,
    name,
    description,
    enabled,
    starttime,
    endtime,
    ownerId,
    gameId,
    startingBalance,
    lastModified,
    groupId,
    disableGroupNotifications,
    groupMessage,
    disableNongroupNotifications,
    nongroupMessage,
    privateViewing
  ) <> (TournamentTable.tupled, TournamentTable.unapply)
}
