'use strict';

// tag::vars[]
const React = require('react'); // <1>
const ReactDOM = require('react-dom'); // <2>
const client = require('./client'); // <3>

const follow = require('./follow');

var root = '/api';
// end::vars[]

// tag::app[]
class App extends React.Component { // <1>




	constructor(props) {
		super(props);
		this.state = {employees: []};
	}

	componentDidMount() { // <2>
		this.loadFromServer(this.state.pageSize);
	}

	loadFromServer(pageSize) {
    	follow(client, root, [
    		{rel: 'employees', params: {size: pageSize}}]
    	).then(employeeCollection => {
    		return client({
    			method: 'GET',
    			path: employeeCollection.entity._links.profile.href,
    			headers: {'Accept': 'application/schema+json'}
    		}).then(schema => {
    			this.schema = schema.entity;
    			return employeeCollection;
    		});
    	}).done(employeeCollection => {
    		this.setState({
    			employees: employeeCollection.entity._embedded.employees,
    			attributes: Object.keys(this.schema.properties),
    			pageSize: pageSize,
    			links: employeeCollection.entity._links});
    	});
    }

	render() { // <3>
		return (
			<EmployeeList employees={this.state.employees}/>
		)
	}
}
// end::app[]

// tag::employee-list[]
class EmployeeList extends React.Component{
	render() {
		const employees = this.props.employees.map(employee =>
			<Employee key={employee._links.self.href} employee={employee}/>
		);
		return (
			<table>
				<tbody>
					<tr>
						<th>First Name</th>
						<th>Last Name</th>
						<th>Description</th>
					</tr>
					{employees}
				</tbody>
			</table>
		)
	}
}
// end::employee-list[]

// tag::employee[]
class Employee extends React.Component{
	render() {
		return (
			<tr>
				<td>{this.props.employee.firstName}</td>
				<td>{this.props.employee.lastName}</td>
				<td>{this.props.employee.description}</td>
			</tr>
		)
	}
}
// end::employee[]

class CreateDialog extends React.Component {

	constructor(props) {
		super(props);
		this.handleSubmit = this.handleSubmit.bind(this);
	}

	handleSubmit(e) {
		e.preventDefault();
		const newEmployee = {};
		this.props.attributes.forEach(attribute => {
			newEmployee[attribute] = ReactDOM.findDOMNode(this.refs[attribute]).value.trim();
		});
		this.props.onCreate(newEmployee);

		// clear out the dialog's inputs
		this.props.attributes.forEach(attribute => {
			ReactDOM.findDOMNode(this.refs[attribute]).value = '';
		});

		// Navigate away from the dialog to hide it.
		window.location = "#";
	}

	onCreate(newEmployee) {
    	follow(client, root, ['employees']).then(employeeCollection => {
    		return client({
    			method: 'POST',
    			path: employeeCollection.entity._links.self.href,
    			entity: newEmployee,
    			headers: {'Content-Type': 'application/json'}
    		})
    	}).then(response => {
    		return follow(client, root, [
    			{rel: 'employees', params: {'size': this.state.pageSize}}]);
    	}).done(response => {
    		if (typeof response.entity._links.last !== "undefined") {
    			this.onNavigate(response.entity._links.last.href);
    		} else {
    			this.onNavigate(response.entity._links.self.href);
    		}
    	});
    }

	render() {
		const inputs = this.props.attributes.map(attribute =>
			<p key={attribute}>
				<input type="text" placeholder={attribute} ref={attribute} className="field"/>
			</p>
		);

		return (
			<div>
				<a href="#createEmployee">Create</a>

				<div id="createEmployee" className="modalDialog">
					<div>
						<a href="#" title="Close" className="close">X</a>

						<h2>Create new employee</h2>

						<form>
							{inputs}
							<button onClick={this.handleSubmit}>Create</button>
						</form>
					</div>
				</div>
			</div>
		)
	}

}



// tag::render[]
ReactDOM.render(
	<App />,
	document.getElementById('react')
)
// end::render[]