
function submitTransfer() {

    const payload = {
        toAccount: document.getElementById("toAccount").value,
        amount: document.getElementById("amount").value,
        description: document.getElementById("desc").value
    };

    fetch("/api/transfer", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(payload)
    })
    .then(res => res.json())
    .then(data => {
        alert(data.message);
    });
}
