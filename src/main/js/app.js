'use strict';

import { EmployeeDetail } from "./employee.js";

const React = require('react');
const ReactDOM = require('react-dom');
const when = require('when');
const client = require('./client');

const follow = require('./follow'); // function to hop multiple links by "rel"

const root = '/api';

ReactDOM.render(
	<EmployeeDetail />,
	document.getElementById('react')
)