/*
 * MIT License
 *
 * Copyright (c) 2020 micro-manager
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

import React from 'react';
import M from 'materialize-css';
import SpecificHostSimulatedMetricsCard from './SpecificHostSimulatedMetricsCard';
import MainLayout from '../shared/MainLayout';
import { Link } from 'react-router-dom';
import {getData} from "../../utils/data";

export default class SpecificHostSimulatedMetrics extends React.Component {
  constructor (props) {
    super(props);
    this.state = { data: [], loading: false, showAdd: true };
  }

  componentDidMount = () => {
    this.loadSimulatedMetrics();
    const instances = M.Tooltip.init(document.querySelectorAll('.tooltipped'));
    this.setState({ tooltipInstances: instances });
  };

  componentWillUnmount = () => {
    this.state.tooltipInstances[0].destroy();
  };

  loadSimulatedMetrics = () => {
    this.setState({ loading: true });
    getData(
      'localhost/metrics/simulated/hosts/specific',
      data => this.setState({ data: data, loading: false })
    );
  };

  renderSimulatedMetrics = () => {
    let simulatedMetricsNodes;
    if (this.state.data) {
      simulatedMetricsNodes = this.state.data.map((simulatedMetric, index) => (
        <SpecificHostSimulatedMetricsCard key={simulatedMetric.id}
                                          simulatedMetric={simulatedMetric}
                                          reloadSimulatedMetrics={this.loadSimulatedMetrics}/>
      ));
    }
    return simulatedMetricsNodes;
  };

  render = () => (
    <MainLayout title='Specific host simulated metrics'>
      {this.renderSimulatedMetrics()}
      <div className="fixed-action-btn tooltipped" data-position="left"
           data-tooltip="Add specific host simulated metric">
        <Link className="waves-effect waves-light btn-floating btn-large grey darken-3"
              to='/metrics/simulated/hosts/specific/detail'>
          <i className="large material-icons">add</i>
        </Link>
      </div>
    </MainLayout>
  );
}
