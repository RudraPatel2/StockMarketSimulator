<h1>Stock Simulator</h1>

<p>Welcome to the Stock Simulator! This project is a simulation tool that allows users to simulate stock trading in a virtual environment. The goal is to offer an educational platform for learning the basics of stock trading, investment strategies, and portfolio management without real financial risk.</p>

<h2>Project Structure</h2>
<ul>
    <li><strong>AccountManagementSystem</strong>: Manages user authentication, account creation, and provides access to different account types.</li>
    <li><strong>YahooFinanceAPI</strong>: Fetches real-time and historical stock data using Yahoo Finance.</li>
</ul>

<h2>Packages</h2>
<ol>
    <li><strong>Accounts Package</strong>
        <ul>
            <li><strong>Account</strong> (Abstract Class): Defines the blueprint for various account types, including <code>displayHistory()</code> and <code>displayCurrent()</code> methods.</li>
            <li><strong>AccountManagementSystem</strong> (Class): Manages login, account creation, and account selection (Bank or Stock Portfolio).</li>
        </ul>
    </li>
    <li><strong>YahooFinanceAPI Package</strong>
        <ul>
            <li><strong>YahooStockAPI</strong> (Class): Connects to Yahoo Finance API, retrieves stock data, and displays historical quotes.</li>
        </ul>
    </li>
</ol>
