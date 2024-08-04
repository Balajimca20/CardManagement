package com.example.animation.ui.cardsdetail

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.Point
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.GridLines
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.LineType
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine
import com.example.animation.commonutils.Constants
import com.example.animation.commonutils.getMonthOnly
import com.example.animation.commonutils.valueOrDefault
import com.example.animation.data.model.CardTransaction
import com.example.animation.ui.utils.isNegative
import com.example.animation.ui.utils.removeNegativeSign

@Preview(showBackground = true)
@Composable
fun LineChartScreenPreview() {
    LineChartScreen(
        selectChartType = Constants.ChartType.DAY.value,
        cardTransactionItem = listOf(
            CardTransaction(
                transaction_id = "txn001",
                date = "2024-07-01T10:15:30Z",
                merchant = "SuperMart",
                status = "success",
                type = "debit",
                amount = 40.67
            ),
            CardTransaction(
                transaction_id = "txn001",
                date = "2024-07-02T10:15:30Z",
                merchant = "SuperMart",
                status = "success",
                type = "debit",
                amount = 90.67
            ),
            CardTransaction(
                transaction_id = "txn001",
                date = "2024-07-03T10:15:30Z",
                merchant = "SuperMart",
                status = "success",
                type = "debit",
                amount = 0.00
            ),
            CardTransaction(
                transaction_id = "txn001",
                date = "2024-07-04T10:15:30Z",
                merchant = "SuperMart",
                status = "success",
                type = "debit",
                amount = 60.67
            ),
            CardTransaction(
                transaction_id = "txn001",
                date = "2024-07-04T10:15:30Z",
                merchant = "SuperMart",
                status = "success",
                type = "debit",
                amount = 20.67
            ),
        )
    )
}

@SuppressLint("DefaultLocale")
@Composable
fun LineChartScreen(selectChartType: String?, cardTransactionItem: List<CardTransaction>?) {

    val tamp =
        getFilterData(cardTransactionItem, selectChartType = selectChartType ?: "") ?: listOf()
    val steps = 5
    val pointsData = tamp.mapIndexed { index, chartMapValue ->
        Point(index.toFloat(), chartMapValue.total)
    }

    val xAxisData = AxisData.Builder()
        .axisStepSize(100.dp)
        .backgroundColor(Color.Transparent)
        .steps(pointsData.size.minus(1))
        .labelData { i -> tamp[i].date }
        .labelAndAxisLinePadding(24.dp)
        .axisLineColor(MaterialTheme.colorScheme.tertiary)
        .axisLabelColor(MaterialTheme.colorScheme.tertiary)
        .build()

    val yAxisData = AxisData.Builder()
        .steps(steps)
        .backgroundColor(Color.Transparent)
        .labelAndAxisLinePadding(24.dp)
        .labelData { i ->
            val ySale = 100 / steps
            "$${(i * ySale)}"
        }
        .axisLineColor(MaterialTheme.colorScheme.tertiary)
        .axisLabelColor(MaterialTheme.colorScheme.tertiary)
        .build()

    val lineChartData = LineChartData(
        linePlotData = LinePlotData(
            lines = listOf(
                Line(
                    dataPoints = pointsData,
                    LineStyle(
                        color = MaterialTheme.colorScheme.tertiary,
                        lineType = LineType.SmoothCurve(isDotted = false),
                    ),
                    IntersectionPoint(
                        color = MaterialTheme.colorScheme.tertiary
                    ),
                    SelectionHighlightPoint(
                        color = MaterialTheme.colorScheme.primary
                    ),
                    ShadowUnderLine(
                        alpha = 0.5f,
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.inversePrimary,
                                Color.Transparent
                            )
                        )
                    ),
                    SelectionHighlightPopUp(
                        popUpLabel = { _, y ->
                            if (isNegative(y.toDouble()))
                            "-$${removeNegativeSign(y.toDouble())}"
                            else
                            "$${removeNegativeSign(y.toDouble())}"
                        }
                    )
                )
            ),
        ),
        backgroundColor = MaterialTheme.colorScheme.surface,
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        gridLines = GridLines(color = MaterialTheme.colorScheme.outlineVariant)
    )
    LineChart(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        lineChartData = lineChartData,
    )

}

fun getFilterData(
    cardTransactionItem: List<CardTransaction>?,
    selectChartType: String
): List<ChartMapValue>? {
    return when (selectChartType) {
        Constants.ChartType.DAY.value -> {
            cardTransactionItem?.groupBy { it.dateAndTime }
                ?.mapValues { entry -> entry.value.sumOf { it.amount ?: 0.00 } }?.map {
                    ChartMapValue(
                        date = it.key.valueOrDefault(),
                        total = it.value.toFloat()
                    )
                }
        }

        Constants.ChartType.MONTH.value -> {
            cardTransactionItem?.groupBy { it.month }
                ?.mapValues { entry -> entry.value.sumOf { it.amount ?: 0.00 } }?.map {
                    ChartMapValue(
                        date = getMonthOnly(it.key.valueOrDefault()),
                        total = it.value.toFloat()
                    )
                }
        }

        Constants.ChartType.YEARLY.value -> {
            cardTransactionItem?.groupBy { it.year }
                ?.mapValues { entry -> entry.value.sumOf { it.amount ?: 0.00 } }?.map {
                    ChartMapValue(
                        date = it.key.valueOrDefault(),
                        total = it.value.toFloat()
                    )
                }
        }

        else -> {
            null
        }
    }
}

data class ChartMapValue(val date: String, val total: Float)