
document.addEventListener("DOMContentLoaded", function () {

    fetch('/api/dashboard')
        .then(response => response.json())
        .then(data => {

            console.log("Dashboard API Data:", data); // ✅ DEBUG

            document.getElementById("userName").innerText = data.userName;
            document.getElementById("balance").innerText = "$" + data.totalBalance;
            document.getElementById("account").innerText =
                "Account: " + data.accountNumber;

            const txnList = document.getElementById("txnList");
            txnList.innerHTML = "";

            data.transactions.forEach(txn => {
                txnList.innerHTML += `
                    <li class="txn ${txn.type}">
                        <div>
                            <strong>${txn.title}</strong><br>
                            <small>${txn.date}</small>
                        </div>
                        <strong>${txn.amount}</strong>
                    </li>
                `;
            });
        })
        .catch(error => console.error("API ERROR:", error));

});
