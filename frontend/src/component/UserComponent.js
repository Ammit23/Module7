import useFetch from "../useFetch";

const Table = () => {
    const {
        error,
        isPending,
        data: users,
    } = useFetch("http://localhost:8085/users");

    console.log(users);

    return (
        <table className="table table-dark table-hover ms-5 mt-5">
            {error && <div>{error}</div>}
            {isPending && <div>Loading...</div>}
            {users && (
                <div className="new">
                    <thead>
                    <tr style={{ textAlign: "center" }}>
                        <th>ID</th>
                        <th>login</th>
                        <th>name</th>
                        <th>role</th>

                    </tr>
                    </thead>
                    {users.map((user) => (
                        <tr key={user.userId}>
                            <td>{user.userId}</td>
                            <td>{user.login}</td>
                            <td>{user.userName}</td>
                            <td>{user.role}</td>
                            <td>
                                <button className="btn btn-success">View</button>
                            </td>
                            <td>
                                <button className="btn btn-warning">Edit</button>
                            </td>
                            <td>
                                <button className="btn btn-danger">Delete</button>
                            </td>
                        </tr>
                    ))}
                </div>
            )}
        </table>
    );
};

export default Table;