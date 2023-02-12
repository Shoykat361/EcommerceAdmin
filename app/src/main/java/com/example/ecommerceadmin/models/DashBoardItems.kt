package com.example.ecommerceadmin.models

import com.example.ecommerceadmin.R

data class DashBoardItems(
    val icon : Int,
    val title:String,
    val type: DashboardItemType
)
enum class DashboardItemType{
    ADD_PRODUCT,VIEW_PRODUCT,ORDER,CATEGORY,USER,REPORT,SETTING
}
val dashBoardItemsList = listOf<DashBoardItems>(
    DashBoardItems(icon = R.drawable.ic_baseline_add_24,"Add Product", type = DashboardItemType.ADD_PRODUCT),
    DashBoardItems(icon = R.drawable.ic_baseline_list_24,"View Product", type = DashboardItemType.VIEW_PRODUCT),
    DashBoardItems(icon = R.drawable.ic_baseline_attach_money_24,"Orders", type = DashboardItemType.ORDER),
    DashBoardItems(icon = R.drawable.ic_baseline_category_24,"Category", type = DashboardItemType.CATEGORY),
    DashBoardItems(icon = R.drawable.ic_baseline_person_pin_24,"User", type = DashboardItemType.USER),
    DashBoardItems(icon = R.drawable.ic_baseline_bar_chart_24,"Report",DashboardItemType.REPORT),
    DashBoardItems(icon = R.drawable.ic_baseline_settings_24,"Settings", type = DashboardItemType.SETTING),
)