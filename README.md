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

<h2>Setup Instructions</h2>

<h3>Prerequisites</h3>
<ul>
    <li>Java Development Kit (JDK) 1.8 or higher</li>
    <li>Maven for dependency management</li>
    <li>Internet Access (for Yahoo Finance API)</li>
</ul>

<h2>How to Use</h2>

<ol>
    <li><strong>Login / Create New Account:</strong>
        <p>On the first screen, choose to either log in or create a new account. New accounts require a full name, username, and password.</p>
    </li>
    <li><strong>Account Selection:</strong>
        <p>After login, you will be prompted to select:
            <ul>
                <li><strong>Stock Portfolio Account:</strong> View stock data, including real-time price, bid, and currency.</li>
                <li><strong>Bank Account:</strong> (Deposit Money, Withdraw Money, and Printing History)</li>
            </ul>
        </p>
    </li>
    <li><strong>Stock Portfolio Account Features:</strong>
        <ul>
            <li><strong>View Stock Information:</strong> Retrieves current price, bid, and currency for the specified stock.</li>
            <li><strong>View Historical Data:</strong> Displays historical prices (high, low, close) for a given stock.</li>
        </ul>
    </li>
</ol>

<h2>Author</h2>
<p>Rudra Patel</p>
