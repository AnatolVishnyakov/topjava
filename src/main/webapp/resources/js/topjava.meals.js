function updateFilteredTable() {
    $.ajax({
        type: "GET",
        url: "ajax/profile/meals/filter",
        data: $("#filter").serialize()
    }).done(updateTableByData);
}

$(function () {
    makeEditable({
        ajaxUrl: "ajax/profile/meals/",
        datatableApi: $("#datatable1").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime"
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
            ]
        }),
        updateTable: updateFilteredTable
    });
});