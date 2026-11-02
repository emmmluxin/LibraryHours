package com.anyuan.library.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.Block
import androidx.compose.material.icons.outlined.LocalLibrary
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.anyuan.library.data.CardStyle
import com.anyuan.library.data.LibraryRepository
import com.anyuan.library.data.LibraryStatus
import com.anyuan.library.ui.components.AppCard
import com.anyuan.library.ui.components.DecorativeSeal
import com.anyuan.library.ui.components.rememberNow
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private val WEEK_LABELS = arrayOf("一", "二", "三", "四", "五", "六", "日")

@Composable
fun LibraryScreen(cardStyle: CardStyle) {
    val now by rememberNow()
    val status = LibraryRepository.statusAt(now)
    val isSummer = LibraryRepository.isSummerSeason(now.monthValue)

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 20.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        item(key = "header") {
            Column {
                Text(
                    text = "图书馆",
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text = "开放时间 · 每周一闭馆",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }

        item(key = "status") {
            StatusCard(status = status, now = now, cardStyle = cardStyle)
        }

        item(key = "week") {
            WeekStrip(now = now)
        }

        item(key = "season") {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = "分季开放时段",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    SeasonCard(
                        title = "5月 – 9月",
                        time = "08:30–20:30",
                        note = "夏令 · 晚间多开一小时",
                        active = isSummer,
                        modifier = Modifier.weight(1f),
                        cardStyle = cardStyle,
                    )
                    SeasonCard(
                        title = "10月 – 4月",
                        time = "08:30–19:30",
                        note = "冬令 · 提前一小时闭馆",
                        active = !isSummer,
                        modifier = Modifier.weight(1f),
                        cardStyle = cardStyle,
                    )
                }
            }
        }
    }
}

@Composable
private fun StatusCard(status: LibraryStatus, now: LocalDateTime, cardStyle: CardStyle) {
    val scheme = MaterialTheme.colorScheme
    val duration = Duration.between(now, status.nextChange)
    val totalMin = duration.toMinutes().coerceAtLeast(0)
    val remainText = if (totalMin >= 60) {
        "${totalMin / 60} 小时 ${totalMin % 60} 分"
    } else {
        "${totalMin} 分钟"
    }
    val statusColor = when {
        status.open -> scheme.primary
        status.closedAllDay -> scheme.error
        else -> scheme.onSurfaceVariant
    }
    val statusIcon = when {
        status.closedAllDay -> Icons.Outlined.Block
        status.open -> Icons.Outlined.LocalLibrary
        else -> Icons.Outlined.Schedule
    }

    AppCard(
        style = cardStyle,
        highlighted = status.open,
        contentPadding = PaddingValues(horizontal = 18.dp, vertical = 18.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            DecorativeSeal(
                tint = statusColor,
                size = 80.dp,
            ) {
                Icon(
                    imageVector = statusIcon,
                    contentDescription = null,
                    tint = statusColor,
                    modifier = Modifier.size(32.dp),
                )
            }
            Spacer(Modifier.height(10.dp))
            Text(
                text = status.statusLine,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = statusColor,
            )
            Spacer(Modifier.height(6.dp))
            if (status.closedAllDay) {
                ClosedHint()
            } else {
                Text(
                    text = "今日 ${status.openTime} – ${status.closeTime}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = scheme.onSurfaceVariant,
                )
            }
            Spacer(Modifier.height(16.dp))
            Surface(
                shape = RoundedCornerShape(18.dp),
                color = scheme.surfaceContainerHigh,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = status.nextChangeLabel,
                        style = MaterialTheme.typography.labelMedium,
                        color = scheme.onSurfaceVariant,
                    )
                    Spacer(Modifier.height(2.dp))
                    Text(
                        text = remainText,
                        style = MaterialTheme.typography.displaySmall,
                        fontWeight = FontWeight.Bold,
                        color = scheme.onSurface,
                    )
                    Spacer(Modifier.height(2.dp))
                    Text(
                        text = "${status.nextChangeLabel.replace("距", "")}时刻 " +
                            status.nextChange.format(DateTimeFormatter.ofPattern("MM月dd日 HH:mm")),
                        style = MaterialTheme.typography.labelSmall,
                        color = scheme.outline,
                    )
                }
            }
        }
    }
}

@Composable
private fun ClosedHint() {
    val scheme = MaterialTheme.colorScheme
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(scheme.surfaceContainerHigh)
            .padding(horizontal = 12.dp, vertical = 7.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = Icons.Outlined.Block,
            contentDescription = null,
            tint = scheme.onSurfaceVariant,
            modifier = Modifier.size(15.dp),
        )
        Spacer(Modifier.width(6.dp))
        Text(
            text = "每周一闭馆",
            style = MaterialTheme.typography.bodySmall,
            color = scheme.onSurfaceVariant,
        )
    }
}

@Composable
private fun WeekStrip(now: LocalDateTime) {
    val scheme = MaterialTheme.colorScheme
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(
            text = "本周",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = scheme.onSurface,
        )
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            for (i in 1..7) {
                val isToday = now.dayOfWeek.value == i
                val isClosed = i == 1
                val accent = if (isClosed) scheme.error else scheme.primary
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = if (isToday) accent.copy(alpha = 0.14f) else Color.Transparent,
                    border = BorderStroke(
                        width = if (isToday) 1.dp else 0.5.dp,
                        color = if (isToday) accent.copy(alpha = 0.85f)
                        else scheme.outlineVariant.copy(alpha = 0.35f),
                    ),
                    modifier = Modifier.weight(1f),
                ) {
                    Column(
                        modifier = Modifier.padding(vertical = 12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = WEEK_LABELS[i - 1],
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = if (isToday) FontWeight.Bold else FontWeight.Normal,
                            color = if (isToday) accent else scheme.onSurface,
                        )
                        Spacer(Modifier.height(3.dp))
                        Text(
                            text = if (isClosed) "闭馆" else "开放",
                            style = MaterialTheme.typography.labelSmall,
                            color = if (isClosed) scheme.error.copy(alpha = 0.9f)
                            else scheme.onSurfaceVariant,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SeasonCard(
    title: String,
    time: String,
    note: String,
    active: Boolean,
    modifier: Modifier = Modifier,
    cardStyle: CardStyle,
) {
    val scheme = MaterialTheme.colorScheme
    AppCard(
        modifier = modifier,
        style = cardStyle,
        highlighted = active,
        contentPadding = PaddingValues(16.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = if (active) scheme.primary else scheme.onSurface,
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text = time,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = scheme.onSurface,
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = note,
                    style = MaterialTheme.typography.labelSmall,
                    color = scheme.onSurfaceVariant,
                )
            }
            if (active) {
                Surface(
                    shape = CircleShape,
                    color = scheme.primary,
                    modifier = Modifier.size(28.dp),
                ) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = null,
                        tint = scheme.onPrimary,
                        modifier = Modifier.padding(7.dp),
                    )
                }
            }
        }
    }
}
