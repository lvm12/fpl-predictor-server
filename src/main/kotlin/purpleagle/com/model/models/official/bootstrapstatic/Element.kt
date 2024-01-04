package purpleagle.com.model.models.official.bootstrapstatic

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
class Element (

    @SerialName("chance_of_playing_next_round"         ) var chanceOfPlayingNextRound         : Int?     = null,
    @SerialName("chance_of_playing_this_round"         ) var chanceOfPlayingThisRound         : Int?     = null,
    @SerialName("code"                                 ) var code                             : Int?     = null,
    @SerialName("cost_change_event"                    ) var costChangeEvent                  : Int?     = null,
    @SerialName("cost_change_event_fall"               ) var costChangeEventFall              : Int?     = null,
    @SerialName("cost_change_start"                    ) var costChangeStart                  : Int?     = null,
    @SerialName("cost_change_start_fall"               ) var costChangeStartFall              : Int?     = null,
    @SerialName("dreamteam_count"                      ) var dreamteamCount                   : Int?     = null,
    @SerialName("element_type"                         ) var elementType                      : Int?     = null,
    @SerialName("ep_next"                              ) var epNext                           : String  = "0",
    @SerialName("ep_this"                              ) var epThis                           : String  = "0",
    @SerialName("event_points"                         ) var eventPoints                      : Int?     = null,
    @SerialName("first_name"                           ) var firstName                        : String?  = null,
    @SerialName("form"                                 ) var form                             : String?  = null,
    @SerialName("id"                                   ) var id                               : Int?     = null,
    @SerialName("in_dreamteam"                         ) var inDreamteam                      : Boolean? = null,
    @SerialName("news"                                 ) var news                             : String?  = null,
    @SerialName("news_added"                           ) var newsAdded                        : String?  = null,
    @SerialName("now_cost"                             ) var nowCost                          : Int?     = null,
    @SerialName("photo"                                ) var photo                            : String?  = null,
    @SerialName("points_per_game"                      ) var pointsPerGame                    : String  = "0.0",
    @SerialName("second_name"                          ) var secondName                       : String?  = null,
    @SerialName("selected_by_percent"                  ) var selectedByPercent                : String?  = null,
    @SerialName("special"                              ) var special                          : Boolean? = null,
    @SerialName("squad_number"                         ) var squadNumber                      : String?  = null,
    @SerialName("status"                               ) var status                           : String?  = null,
    @SerialName("team"                                 ) var team                             : Int?     = null,
    @SerialName("team_code"                            ) var teamCode                         : Int?     = null,
    @SerialName("total_points"                         ) var totalPoints                      : Int?     = null,
    @SerialName("transfers_in"                         ) var transfersIn                      : Int?     = null,
    @SerialName("transfers_in_event"                   ) var transfersInEvent                 : Int?     = null,
    @SerialName("transfers_out"                        ) var transfersOut                     : Int?     = null,
    @SerialName("transfers_out_event"                  ) var transfersOutEvent                : Int?     = null,
    @SerialName("value_form"                           ) var valueForm                        : String?  = null,
    @SerialName("value_season"                         ) var valueSeason                      : String?  = null,
    @SerialName("web_name"                             ) var webName                          : String?  = null,
    @SerialName("minutes"                              ) var minutes                          : Int?     = null,
    @SerialName("goals_scored"                         ) var goalsScored                      : Int?     = null,
    @SerialName("assists"                              ) var assists                          : Int?     = null,
    @SerialName("clean_sheets"                         ) var cleanSheets                      : Int?     = null,
    @SerialName("goals_conceded"                       ) var goalsConceded                    : Int?     = null,
    @SerialName("own_goals"                            ) var ownGoals                         : Int?     = null,
    @SerialName("penalties_saved"                      ) var penaltiesSaved                   : Int?     = null,
    @SerialName("penalties_missed"                     ) var penaltiesMissed                  : Int?     = null,
    @SerialName("yellow_cards"                         ) var yellowCards                      : Int?     = null,
    @SerialName("red_cards"                            ) var redCards                         : Int?     = null,
    @SerialName("saves"                                ) var saves                            : Int?     = null,
    @SerialName("bonus"                                ) var bonus                            : Int?     = null,
    @SerialName("bps"                                  ) var bps                              : Int?     = null,
    @SerialName("influence"                            ) var influence                        : String?  = null,
    @SerialName("creativity"                           ) var creativity                       : String?  = null,
    @SerialName("threat"                               ) var threat                           : String?  = null,
    @SerialName("ict_index"                            ) var ictIndex                         : String?  = null,
    @SerialName("starts"                               ) var starts                           : Int?     = null,
    @SerialName("expected_goals"                       ) var expectedGoals                    : String?  = null,
    @SerialName("expected_assists"                     ) var expectedAssists                  : String?  = null,
    @SerialName("expected_goal_involvements"           ) var expectedGoalInvolvements         : String?  = null,
    @SerialName("expected_goals_conceded"              ) var expectedGoalsConceded            : String?  = null,
    @SerialName("influence_rank"                       ) var influenceRank                    : Int?     = null,
    @SerialName("influence_rank_type"                  ) var influenceRankType                : Int?     = null,
    @SerialName("creativity_rank"                      ) var creativityRank                   : Int?     = null,
    @SerialName("creativity_rank_type"                 ) var creativityRankType               : Int?     = null,
    @SerialName("threat_rank"                          ) var threatRank                       : Int?     = null,
    @SerialName("threat_rank_type"                     ) var threatRankType                   : Int?     = null,
    @SerialName("ict_index_rank"                       ) var ictIndexRank                     : Int?     = null,
    @SerialName("ict_index_rank_type"                  ) var ictIndexRankType                 : Int?     = null,
    @SerialName("corners_and_indirect_freekicks_order" ) var cornersAndIndirectFreekicksOrder : Int?  = null,
    @SerialName("corners_and_indirect_freekicks_text"  ) var cornersAndIndirectFreekicksText  : String?  = null,
    @SerialName("direct_freekicks_order"               ) var directFreekicksOrder             : Int?  = null,
    @SerialName("direct_freekicks_text"                ) var directFreekicksText              : String?  = null,
    @SerialName("penalties_order"                      ) var penaltiesOrder                   : Int?  = null,
    @SerialName("penalties_text"                       ) var penaltiesText                    : String?  = null,
    @SerialName("expected_goals_per_90"                ) var expectedGoalsPer90               : Float?     = null,
    @SerialName("saves_per_90"                         ) var savesPer90                       : Float?     = null,
    @SerialName("expected_assists_per_90"              ) var expectedAssistsPer90             : Float?     = null,
    @SerialName("expected_goal_involvements_per_90"    ) var expectedGoalInvolvementsPer90    : Float?     = null,
    @SerialName("expected_goals_conceded_per_90"       ) var expectedGoalsConcededPer90       : Float?     = null,
    @SerialName("goals_conceded_per_90"                ) var goalsConcededPer90               : Float?     = null,
    @SerialName("now_cost_rank"                        ) var nowCostRank                      : Int?     = null,
    @SerialName("now_cost_rank_type"                   ) var nowCostRankType                  : Int?     = null,
    @SerialName("form_rank"                            ) var formRank                         : Int?     = null,
    @SerialName("form_rank_type"                       ) var formRankType                     : Int?     = null,
    @SerialName("points_per_game_rank"                 ) var pointsPerGameRank                : Int?     = null,
    @SerialName("points_per_game_rank_type"            ) var pointsPerGameRankType            : Int?     = null,
    @SerialName("selected_rank"                        ) var selectedRank                     : Int?     = null,
    @SerialName("selected_rank_type"                   ) var selectedRankType                 : Int?     = null,
    @SerialName("starts_per_90"                        ) var startsPer90                      : Float?     = null,
    @SerialName("clean_sheets_per_90"                  ) var cleanSheetsPer90                 : Float?     = null

)

