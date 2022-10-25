function input() {
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

    // 家計簿の詳細を取得する
    $.ajax({
        url: BASE_URL + `/expenses?groupid=${groupId}&month=${month}`,
        type: "GET",
        async: true,
        dataType: "json",
        contentType: "application/json",
        timeout: 5000,
        cache: false,
        success: function (responseData) {
            // 成功時処理
            var expenses = responseData.expenses;
            sessionStorage.setItem('expenses', JSON.stringify(expenses));

            for (let i = 0; i < expenses.length; i++) {
                $('#detailbody').append('<tr>'
                    + '<th scope="row">' + expenses[i].date + '</th>'
                    + '<td>' + expenses[i].memberName + '</td>'
                    + '<td>' + expenses[i].categoryName + '</td>'
                    + '<td>' + expenses[i].price + '</td>'
                    + '<td>' + expenses[i].detail + '</td>'
                    + '<td><button type="button" class="btn btn-danger delete-btn" data-bs-toggle="modal" data-bs-target="#delete" id="' + i + '">削除</button></td>'
                    + '</tr>');
            }
        },
        error: function (xhr, textStatus, errorThrown) {
            // 失敗時処理
            window.location.href = "error";
        },
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

    // メンバーの一覧を取得する
    $.ajax({
        url: BASE_URL + `/members?groupid=${groupId}`,
        type: "GET",
        async: true,
        dataType: "json",
        contentType: "application/json",
        timeout: 5000,
        cache: false,
        success: function (responseData) {
            // 成功時処理
            var members = responseData.members;
            for (let i = 0; i < members.length; i++) {
                $('#expenseMember').append($('<option>').html(members[i].memberName).val(members[i].memberId));
            }
        },
        error: function (xhr, textStatus, errorThrown) {
            // 失敗時処理
            window.location.href = "error";
        },
    });

    // 項目の一覧を取得する
    $.ajax({
        url: BASE_URL + `/categories?groupid=${groupId}`,
        type: "GET",
        async: true,
        dataType: "json",
        contentType: "application/json",
        timeout: 5000,
        cache: false,
        success: function (responseData) {
            // 成功時処理
            var categories = responseData.categories;
            for (let i = 0; i < categories.length; i++) {
                $('#expenseCategory').append($('<option>').html(categories[i].categoryName).val(categories[i].categoryId));
            }
        },
        error: function (xhr, textStatus, errorThrown) {
            // 失敗時処理
            window.location.href = "error";
        },
    });
}

function categoryReg() {
    let name = document.getElementById('categoryName');
    var arr = { groupid: groupId, categoryName: name.value };
    var member_info = JSON.stringify(arr);

    $.ajax({
        url: BASE_URL + `/category`,
        type: "POST",
        async: true,
        dataType: "json",
        contentType: "application/json",
        timeout: 5000,
        cache: false,
        data: member_info,
        success: function (responseData) {
            // 成功時処理
            $('#addCategory').modal();
            $('#addcCtegory').modal('hide');

            // リロードする
            window.location.reload();
        },
        error: function (xhr, textStatus, errorThrown) {
            // 失敗時処理
            var res = $.parseJSON(xhr.responseText);
            $('.text-danger').text(res.errorMessage);
        },
    });
}

function expenseReg() {
    let date = document.getElementById('expenseDate').value;
    date = date.replace(/-/g, '');
    let memberId = document.getElementById('expenseMember').value;
    let categoryId = document.getElementById('expenseCategory').value;
    let price = document.getElementById('expensePrice').value;
    let detail = document.getElementById('expenseDetail').value;
    var arr = {
        groupid: groupId,
        date: date,
        memberid: memberId,
        categoryid: categoryId,
        price: price,
        detail: detail
    };
    var expense_info = JSON.stringify(arr);

    // 家計簿の登録を行う
    $.ajax({
        url: BASE_URL + `/expense`,
        type: "POST",
        async: true,
        dataType: "json",
        contentType: "application/json",
        timeout: 5000,
        cache: false,
        data: expense_info,
        success: function (responseData) {
            // 成功時処理
            $('#addExpense').modal();
            $('#addExpense').modal('hide');

            // リロードする
            window.location.reload();
        },
        error: function (xhr, textStatus, errorThrown) {
            // 失敗時処理
            var res = $.parseJSON(xhr.responseText);
            $('.text-danger').text(res.errorMessage);
        },
    });
}

function deleteReg(e) {
    var id = e.target.id;
    var expenses = JSON.parse(sessionStorage.getItem('expenses'));
    $('#delDate').text("日付：" + expenses[id].date);
    $('#delMem').text("メンバー：" + expenses[id].memberName);
    $('#delCat').text("項目：" + expenses[id].categoryName);
    $('#delPrice').text("金額：" + expenses[id].price);
    $('#delDet').text("内容：" + expenses[id].detail);

    var arr = {
        groupid: groupId,
        expensesid: expenses[id].expensesId,
        month: month,
    };
    var expense_info = JSON.stringify(arr);

    $('#deleteReg').on('click', function () {
        // 家計簿の更新を行う(削除)
        $.ajax({
            url: BASE_URL + `/expense`,
            type: "DELETE",
            async: true,
            dataType: "json",
            contentType: "application/json",
            timeout: 5000,
            cache: false,
            data: expense_info,
            success: function (responseData) {
                // 成功時処理
                $('#delete').modal();
                $('#delete').modal('hide');

                // リロードする
                window.location.reload();
            },
            error: function (xhr, textStatus, errorThrown) {
                // 失敗時処理
                window.location.href = "error";
            },
        });
    });
}