<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>PSNS Bank | Transfer Money</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Icons -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">

    <style>
        body { background-color: #f6f8fb; }

        .sidebar {
            width: 260px;
            min-height: 100vh;
            position: fixed;
            background: linear-gradient(180deg, #0b1d3a, #020617);
            color: white;
        }

        .sidebar .logo {
            font-size: 20px;
            font-weight: bold;
            padding: 20px;
            border-bottom: 1px solid rgba(255,255,255,0.1);
        }

        .sidebar a {
            color: #cfd8dc;
            text-decoration: none;
            padding: 14px 22px;
            display: flex;
            align-items: center;
            gap: 12px;
        }

        .sidebar a.active,
        .sidebar a:hover {
            background-color: #0d6efd;
            color: white;
        }

        .sidebar .logout {
            position: absolute;
            bottom: 20px;
            width: 100%;
        }

        .main {
            margin-left: 260px;
        }

        .card-transfer {
            max-width: 600px;
        }

        @media(max-width:768px){
            .sidebar {
                position: static;
                width: 100%;
            }
            .main {
                margin-left: 0;
            }
        }
    </style>
</head>
<body>

<!-- SIDEBAR -->
<div class="sidebar">
    <div class="logo">
        <i class="bi bi-credit-card-2-front"></i> PSNS BANK
    </div>

    <a href="/dashboard"><i class="bi bi-house"></i> Dashboard</a>
    <a href="/transfer" class="active"><i class="bi bi-send"></i> Transfer Money</a>
    <a href="/transaction"><i class="bi bi-clock-history"></i> Transactions</a>
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
        <span class="navbar-brand fw-semibold">Transfer Money</span>
        <div class="d-flex align-items-center gap-2">
            <i class="bi bi-person-circle fs-3 text-primary"></i>
            <span>John Doe</span>
        </div>
    </nav>

    <!-- TRANSFER FORM -->
    <div class="container-fluid p-4">
        <div class="card shadow-sm rounded-4 mx-auto card-transfer">
            <div class="card-body p-4">

                <form action="/transfer-money" method="post">

                    <!-- Recipient -->
                    <div class="mb-3">
                        <label class="form-label">Recipient Account</label>
                        <input type="text" name="recipient"
                               class="form-control"
                               placeholder="Enter account number or email"
                               required>
                    </div>

                    <!-- Amount -->
                    <div class="mb-3">
                        <label class="form-label">Amount</label>
                        <div class="input-group">
                            <span class="input-group-text">$</span>
                            <input type="number"
                                   name="amount"
                                   class="form-control"
                                   step="0.01"
                                   placeholder="0.00"
                                   required>
                        </div>
                    </div>

                    <!-- Description -->
                    <div class="mb-3">
                        <label class="form-label">Description (Optional)</label>
                        <textarea name="description"
                                  class="form-control"
                                  rows="3"
                                  placeholder="What's this transfer for?"></textarea>
                    </div>

                    <!-- Transfer Type -->
                    <div class="mb-3">
                        <label class="form-label">Transfer Type</label>
                        <select name="type" class="form-select">
                            <option value="INSTANT">Instant Transfer</option>
                            <option value="SCHEDULED">Scheduled Transfer</option>
                        </select>
                    </div>

                    <!-- Balance -->
                    <div class="alert alert-info">
                        Available Balance: <strong>$45,823.50</strong>
                    </div>

                    <!-- Submit -->
					
					<button onclick="submitTransfer()" type="submit" class="btn btn-primary w-100">
					    Transfer Money
					</button>


                </form>

            </div>
        </div>
    </div>
</div>

</body>
</html>
