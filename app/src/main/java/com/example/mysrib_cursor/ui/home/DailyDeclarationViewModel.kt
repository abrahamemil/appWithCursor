package com.example.mysrib_cursor.ui.home

import androidx.lifecycle.ViewModel

class DailyDeclarationViewModel : ViewModel() {
    var submitDate: String = ""
    var submitTime: String = ""
    var name: String = "Cursor AI"
    var genId: String = "123456"
    var isInBangalore: Boolean = false
    var haveAnySymptoms: Boolean = false
    var workStatus: String = ""
    var weeklyPlan: Map<String, String> = emptyMap()
} 