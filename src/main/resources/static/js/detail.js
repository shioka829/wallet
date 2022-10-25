function detail() {
    // 月をセッションストレージから取得(再読み込み時)
    var month = sessionStorage.getItem('month');

    // datepickerの設定
    $('#datepicker').datepicker({
        language: "ja",
        format: "yyyy年mm月",
        minViewMode: 1,
        maxViewMode: 2,
    });

    // datepickerの初期値を設定
    if (month == null) {
        $("#datepicker").datepicker("setDate", "2014/02/15");
    } else {
        var yyyy = month.substring(0, 4);
        var mm = month.substring(4, 6);
        var yyyyMM = yyyy + "年" + mm + "月";
        $("#datepicker").datepicker("setDate", yyyyMM);
    }

    // 初期の値を取得
    var month = $("#datepicker").datepicker().val();
    month = month.replace(/年/g, '');
    month = month.replace(/月/g, '');
    sessionStorage.setItem('month', month);

    // 日付変更時の処理
    $('#datepicker').on({
        changeDate:
            function (obj) {
                var yyyy = obj.date.getFullYear();
                var mm = obj.date.getMonth() + 1;
                month = String(yyyy) + String(mm);
                sessionStorage.setItem('month', month);
                window.location.reload();
            }
    });

    // 家計簿の小計・合計を取得する
    $.ajax({
        url: BASE_URL + `/expenses-total?groupid=${groupId}&month=${month}`,
        type: "GET",
        async: true,
        dataType: "json",
        contentType: "application/json",
        timeout: 5000,
        cache: false,
        success: function (responseData) {
            // 成功時処理
            var total = responseData.total;
            var memberSubTotal = responseData.memberSubTotal;
            var categorySubTotal = responseData.categorySubTotal;

            // 合計を追加
            $("#total-price").text(total + '円');

            // 会員ごとの合計を追加
            for (let i = 0; i < memberSubTotal.length; i++) {
                $('#memberbody').append('<tr><td>' + memberSubTotal[i].name + '</td><td>' + memberSubTotal[i].total + '</tr>');
            }

            // 項目ごとの合計を追加
            for (let i = 0; i < categorySubTotal.length; i++) {
                $('#categorybody').append('<tr><td>' + categorySubTotal[i].name + '</td><td>' + categorySubTotal[i].total + '</tr>');
            }
        },
        error: function (xhr, textStatus, errorThrown) {
            // 失敗時処理
            window.location.href = "error";
        },
    });
}