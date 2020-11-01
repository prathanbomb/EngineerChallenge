package com.weekly.engineer.challenge.data;

import android.provider.BaseColumns;

public class DataContract {
    public static final String DATABASE_NAME = "engineerdb.db";

    public static final class HistoryEntry implements BaseColumns {
        public static final String TABLE_NAME = "history";

        public static final String SUBJECT = "subject";

        public static final String CORRECT = "correct";

    }

    public static final class PersonalEntry implements BaseColumns {
        public static final String TABLE_NAME = "personal";

        public static final String COIN = "game_coin";
        public static final String HELP_1 = "help1";
        public static final String HELP_2 = "help2";

        public static final String HI_SUB1 = "hi_1";
        public static final String HI_SUB2 = "hi_2";
        public static final String HI_SUB3 = "hi_3";
        public static final String HI_SUB4 = "hi_4";

        public static final String GAME_PERFECT = "game_per";
        public static final String GAME_EX = "game_ex";
        public static final String GAME_GOOD = "game_good";
        public static final String USE_COIN = "use_coin";
        public static final String GAME_CORRECT_PERFECT = "game_correct_perfect";
        public static final String GAME_STAR = "game_star";
        public static final String GAME_TRY = "game_try";
        public static final String PLAY_GAME = "game";
        public static final String Q_GAME_SUB1 = "q_game_com";
        public static final String Q_GAME_SUB2 = "q_game_mat";
        public static final String Q_GAME_SUB3 = "q_game_mech";
        public static final String Q_GAME_SUB4 = "q_game_draw";

        public static final String Q_TEST_ALL = "q_test_all";
        public static final String Q_TEST_SUB1 = "q_test_com";
        public static final String Q_TEST_SUB2 = "q_test_mat";
        public static final String Q_TEST_SUB3 = "q_test_mech";
        public static final String Q_TEST_SUB4 = "q_test_draw";
        public static final String Q_TEST_MORE_SUB1 = "q_test_more_com";
        public static final String Q_TEST_MORE_SUB2 = "q_test_more_mat";
        public static final String Q_TEST_MORE_SUB3 = "q_test_more_mech";
        public static final String Q_TEST_MORE_SUB4 = "q_test_more_draw";

        public static final String[] ACHIEVEMENT = {
                GAME_PERFECT,
                GAME_EX,
                GAME_GOOD,
                USE_COIN,
                GAME_CORRECT_PERFECT,
                GAME_STAR,
                GAME_TRY,
                PLAY_GAME,
                Q_GAME_SUB1,
                Q_GAME_SUB2,
                Q_GAME_SUB3,
                Q_GAME_SUB4,
                Q_TEST_ALL,
                Q_TEST_SUB1,
                Q_TEST_SUB2,
                Q_TEST_SUB3,
                Q_TEST_SUB4,
                Q_TEST_MORE_SUB1,
                Q_TEST_MORE_SUB2,
                Q_TEST_MORE_SUB3,
                Q_TEST_MORE_SUB4
        };

    }

    public static final class AchievementEntry implements BaseColumns {
        public static final String TABLE_NAME = "achievement";

        public static final String NAME = "name";
        public static final String CONDITION = "condition";
        public static final String GOAL = "goal";
        public static final String TYPE = "type";
        public static final String DETAIL = "detail";
        public static final String HAVE = "have";
        public static final String IMG = "img";
    }

}
