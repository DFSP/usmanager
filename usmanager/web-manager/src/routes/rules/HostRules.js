/*
 * MIT License
 *
 * Copyright (c) 2020 usmanager
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
import {getData} from "../../utils/api";

export default class HostRules extends React.Component {
  constructor (props) {
    super(props);
    this.state = { hostRules: [], loading: false };
  }

  componentDidMount() {
    this.loadHostRules();
  };

  loadHostRules = () => {
    this.setState({ loading: true });
    getData(
      `http://localhost:8080/hosts/'${this.props.host.hostname}'/rules`,
      data => this.setState({ hostRules: data, loading: false })
    );
  };

  render = () => (
    <div>
      <h5>Rules</h5>
      {this.state.hostRules && this.state.hostRules.map(hostRule => (
        <div key={hostRule.rule.id}>
          <div className='card'>
            <div className='card-content'>
              {hostRule.rule.ruleName}
            </div>
          </div>
        </div>
      ))}
    </div>
  );
}
