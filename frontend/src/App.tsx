import { useState } from 'react';
import './App.css';

type CalculateRequest = {
    amount: number;
    months: number;
    rate: number;
};

type CalculateResponse = {
    total: number;
    profit: number;
};

function App() {
    const [amount, setAmount] = useState('');
    const [months, setMonths] = useState('');
    const [rate, setRate] = useState('');

    const [result, setResult] = useState<CalculateResponse | null>(null);

    const [error, setError] = useState('');

    const [loading, setLoading] = useState(false);

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setError('');
        setResult(null);
        setLoading(true);

        try {
            const request: CalculateRequest = {
                amount: Number(amount),
                months: Number(months),
                rate: Number(rate),
            };

            const response = await fetch('/api/calculate', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(request),
            });

            if (!response.ok) {
                throw new Error('Сервер вернул ошибку. Проверьте введённые данные.');
            }

            const data: CalculateResponse = await response.json();
            setResult(data);
        } catch (err) {
            setError(err instanceof Error ? err.message : 'Неизвестная ошибка');
        } finally {
            setLoading(false);
        }
    };

    const formatRub = (value: number) =>
        new Intl.NumberFormat('ru-RU', {
            style: 'currency',
            currency: 'RUB',
            minimumFractionDigits: 2,
        }).format(value);

    return (
        <div className="container">
            <h1>Калькулятор вклада</h1>

            <form onSubmit={handleSubmit} className="form">
                <label>
                    Сумма вклада (₽)
                    <input
                        type="number"
                        value={amount}
                        onChange={(e) => setAmount(e.target.value)}
                        min="1000"
                        max="10000000"
                        required
                    />
                </label>

                <label>
                    Срок (месяцы)
                    <input
                        type="number"
                        value={months}
                        onChange={(e) => setMonths(e.target.value)}
                        min="1"
                        max="60"
                        required
                    />
                </label>

                <label>
                    Годовая ставка (%)
                    <input
                        type="number"
                        value={rate}
                        onChange={(e) => setRate(e.target.value)}
                        min="1"
                        max="20"
                        step="0.1"
                        required
                    />
                </label>

                <button type="submit" disabled={loading}>
                    {loading ? 'Расчёт...' : 'Рассчитать'}
                </button>
            </form>

            {error && <div className="error">{error}</div>}

            {result && (
                <div className="result">
                    <p>Начальная сумма: {formatRub(Number(amount))}</p>
                    <p>Итоговая сумма: {formatRub(result.total)}</p>
                    <p>Доход: {formatRub(result.profit)}</p>
                </div>
            )}
        </div>
    );
}

export default App;