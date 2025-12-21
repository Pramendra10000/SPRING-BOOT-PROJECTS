<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>PSNS Bank | Transactions</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Icons -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
	<link href="Css/Transaction.css" rel="stylesheet">
  
</head>
<body>

<!-- SIDEBAR -->
<div class="sidebar">
    <div class="logo">
        <i class="bi bi-credit-card-2-front"></i> PSNS BANK
    </div>

    <a href="/dashboard"><i class="bi bi-house"></i> Dashboard</a>
    <a href="/transfer"><i class="bi bi-send"></i> Transfer Money</a>
    <a href="/transaction" class="active"><i class="bi bi-clock-history"></i> Transactions</a>
    <a href="/account"><i class="bi bi-file-text"></i> Account Details</a>
    <a href="/profile"><i class="bi bi-person"></i> Profile</a>

    <div class="logout">
        <a href="/logout" class="text-danger">
            <i class="bi bi-box-arrow-right"></i> Logout
        </a>
    </div>
</div>

<!-- MAIN CONTENT -->
<div class="main">

    <!-- TOP BAR -->
    <nav class="navbar bg-white shadow-sm px-4">
        <span class="navbar-brand fw-semibold">Transaction History</span>
        <div class="d-flex align-items-center gap-2">
            <i class="bi bi-person-circle fs-3 text-primary"></i>
            <span>John Doe</span>
        </div>
    </nav>

    <!-- CONTENT -->
    <div class="container-fluid p-4">
        <div class="bg-white rounded-4 shadow-sm p-4">

            <div class="d-flex justify-content-between align-items-center mb-3">
                <h6 class="mb-0">All Transactions</h6>

                <div class="d-flex gap-2">
                    <select class="form-select form-select-sm">
                        <option>All Types</option>
                        <option>Credit</option>
                        <option>Debit</option>
                    </select>

                    <select class="form-select form-select-sm">
                        <option>Last 30 Days</option>
                        <option>Last 6 Months</option>
                        <option>Last 1 Year</option>
                    </select>
                </div>
            </div>

            <div class="table-responsive">
                <table class="table align-middle">
                    <thead class="text-muted">
                    <tr>
                        <th>Date</th>
                        <th>Description</th>
                        <th>Type</th>
                        <th>Amount</th>
                        <th>Balance</th>
                    </tr>
                    </thead>
                    <tbody>

                    <tr>
                        <td>2024-12-15</td>
                        <td class="d-flex align-items-center gap-2">
                            <div class="icon-box icon-credit">
                                <i class="bi bi-arrow-down-left"></i>
                            </div>
                            Salary Credit
                        </td>
                        <td><span class="badge badge-credit">Credit</span></td>
                        <td class="text-success">+$5000.00</td>
                        <td>$45823.50</td>
                    </tr>

                    <tr>
                        <td>2024-12-14</td>
                        <td class="d-flex align-items-center gap-2">
                            <div class="icon-box icon-debit">
                                <i class="bi bi-arrow-up-right"></i>
                            </div>
                            Grocery Store
                        </td>
                        <td><span class="badge badge-debit">Debit</span></td>
                        <td class="text-danger">-$150.00</td>
                        <td>$40823.50</td>
                    </tr>

                    <tr>
                        <td>2024-12-13</td>
                        <td class="d-flex align-items-center gap-2">
                            <div class="icon-box icon-debit">
                                <i class="bi bi-arrow-up-right"></i>
                            </div>
                            Restaurant
                        </td>
                        <td><span class="badge badge-debit">Debit</span></td>
                        <td class="text-danger">-$80.00</td>
                        <td>$40973.50</td>
                    </tr>

                    <tr>
                        <td>2024-12-12</td>
                        <td class="d-flex align-items-center gap-2">
                            <div class="icon-box icon-credit">
                                <i class="bi bi-arrow-down-left"></i>
                            </div>
                            Refund
                        </td>
                        <td><span class="badge badge-credit">Credit</span></td>
                        <td class="text-success">+$200.00</td>
                        <td>$41053.50</td>
                    </tr>

                    <tr>
                        <td>2024-12-10</td>
                        <td class="d-flex align-items-center gap-2">
                            <div class="icon-box icon-debit">
                                <i class="bi bi-arrow-up-right"></i>
                            </div>
                            Rent Payment
                        </td>
                        <td><span class="badge badge-debit">Debit</span></td>
                        <td class="text-danger">-$1200.00</td>
                        <td>$40853.50</td>
                    </tr>

                    </tbody>
                </table>
            </div>

        </div>
    </div>
</div>

</body>
</html>
