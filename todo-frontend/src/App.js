import React, { useState, useEffect } from 'react';
import { PlusCircle, Trash2, Check, X } from 'lucide-react';

export default function TodoApp() {
  const [todos, setTodos] = useState([]);
  const [newTodo, setNewTodo] = useState('');
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  // Fetch todos from the backend
  const fetchTodos = async () => {
    try {
      const response = await fetch('http://localhost:8080/api/todos');
      const data = await response.json();
      setTodos(data);
      setLoading(false);
    } catch (err) {
      setError('Failed to fetch todos');
      setLoading(false);
    }
  };

  // Add new todo
  const addTodo = async (e) => {
    e.preventDefault();
    if (!newTodo.trim()) return;

    try {
      const response = await fetch('http://localhost:8080/api/todos/add', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ title: newTodo }),
      });
      const data = await response.json();
      setTodos([...todos, data]);
      setNewTodo('');
    } catch (err) {
      setError('Failed to add todo');
    }
  };

  // Toggle todo completion
  const toggleTodo = async (id, completed) => {
    try {
      const todo = todos.find(t => t.id === id);
      const response = await fetch(`http://localhost:8080/api/todos/${id}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ ...todo, completed: !completed }),
      });
      const updatedTodo = await response.json();
      setTodos(todos.map(t => t.id === id ? updatedTodo : t));
    } catch (err) {
      setError('Failed to update todo');
    }
  };

  // Delete todo
  const deleteTodo = async (id) => {
    try {
      await fetch(`http://localhost:8080/api/todos/${id}`, {
        method: 'DELETE',
      });
      setTodos(todos.filter(t => t.id !== id));
    } catch (err) {
      setError('Failed to delete todo');
    }
  };

  useEffect(() => {
    fetchTodos();
  }, []);

  if (loading) {
    return (
      <div className="flex items-center justify-center min-h-screen">
        <div className="text-lg text-gray-600">Loading...</div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gray-100 py-8">
      <div className="max-w-2xl mx-auto px-4">
        <div className="bg-white rounded-lg shadow-md overflow-hidden">
          <div className="p-6">
            <h1 className="text-2xl font-bold text-gray-800 mb-6">Todo List</h1>
            
            {/* Add Todo Form */}
            <form onSubmit={addTodo} className="mb-6 flex gap-2">
              <input
                type="text"
                value={newTodo}
                onChange={(e) => setNewTodo(e.target.value)}
                placeholder="Add a new todo..."
                className="flex-1 px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
              />
              <button
                type="submit"
                className="px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500"
              >
                <PlusCircle className="w-5 h-5" />
              </button>
            </form>

            {/* Error Message */}
            {error && (
              <div className="mb-4 p-4 bg-red-100 text-red-700 rounded-md flex items-center justify-between">
                <span>{error}</span>
                <button onClick={() => setError(null)}>
                  <X className="w-5 h-5" />
                </button>
              </div>
            )}

            {/* Todo List */}
            <div className="space-y-2">
              {todos.map((todo) => (
                <div
                  key={todo.id}
                  className="flex items-center justify-between p-4 bg-gray-50 rounded-md"
                >
                  <div className="flex items-center gap-3 flex-1">
                    <button
                      onClick={() => toggleTodo(todo.id, todo.completed)}
                      className={`p-1 rounded-full ${
                        todo.completed ? 'bg-green-500 text-white' : 'bg-gray-200'
                      }`}
                    >
                      <Check className="w-4 h-4" />
                    </button>
                    <span className={`${todo.completed ? 'line-through text-gray-500' : 'text-gray-800'}`}>
                      {todo.title}
                    </span>
                  </div>
                  <button
                    onClick={() => deleteTodo(todo.id)}
                    className="text-red-500 hover:text-red-600"
                  >
                    <Trash2 className="w-5 h-5" />
                  </button>
                </div>
              ))}
              {todos.length === 0 && (
                <div className="text-center py-6 text-gray-500">
                  No todos yet. Add one above!
                </div>
              )}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}