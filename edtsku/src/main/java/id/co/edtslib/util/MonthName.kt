package id.co.edtslib.util

import android.content.Context
import id.co.edtslib.R

enum class MonthName {
    January {
        override fun toName(context: Context) = context.getString(R.string.january)
        override fun toNameFormatMmm(context: Context) = context.getString(R.string.january_mmm)
    },
    February {
        override fun toName(context: Context) = context.getString(R.string.february)
        override fun toNameFormatMmm(context: Context) = context.getString(R.string.february_mmm)
    },
    March {
        override fun toName(context: Context) = context.getString(R.string.march)
        override fun toNameFormatMmm(context: Context) = context.getString(R.string.march_mmm)
    },
    April {
        override fun toName(context: Context) = context.getString(R.string.april)
        override fun toNameFormatMmm(context: Context) = context.getString(R.string.april_mmm)
    },
    May {
        override fun toName(context: Context) = context.getString(R.string.may)
        override fun toNameFormatMmm(context: Context) = context.getString(R.string.may_mmm)
    },
    June {
        override fun toName(context: Context) = context.getString(R.string.june)
        override fun toNameFormatMmm(context: Context) = context.getString(R.string.june_mmm)
    },
    July {
        override fun toName(context: Context) = context.getString(R.string.july)
        override fun toNameFormatMmm(context: Context) = context.getString(R.string.july_mmm)
    },
    August {
        override fun toName(context: Context) = context.getString(R.string.august)
        override fun toNameFormatMmm(context: Context) = context.getString(R.string.august_mmm)
    },
    September {
        override fun toName(context: Context) = context.getString(R.string.september)
        override fun toNameFormatMmm(context: Context) = context.getString(R.string.september_mmm)
    },
    October {
        override fun toName(context: Context) = context.getString(R.string.october)
        override fun toNameFormatMmm(context: Context) = context.getString(R.string.october_mmm)
    },
    November {
        override fun toName(context: Context) = context.getString(R.string.november)
        override fun toNameFormatMmm(context: Context) = context.getString(R.string.november_mmm)
    },
    December {
        override fun toName(context: Context) = context.getString(R.string.december)
        override fun toNameFormatMmm(context: Context) = context.getString(R.string.december_mmm)
    };

    abstract fun toName(context: Context): String
    abstract fun toNameFormatMmm(context: Context): String
}