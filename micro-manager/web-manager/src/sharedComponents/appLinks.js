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
import { NavLink } from 'react-router-dom';

export class AppLinks extends React.Component {
  constructor (props) {
    super(props);
    const navLinks = this.props.links;
    this.state = { links: navLinks };
  }

    renderNavLinks = () => {
      return this.state.links.map(function (link, index) {
        return (
          <NavLink key={index} exact to={link.link} className='collection-item' activeClassName='active'>
            {link.name}
          </NavLink>
        );
      });
    };

    render () {
      return (
        <div className='col s12 m4 hide-on-small-only'>
          <div className='collection'>
            {this.renderNavLinks()}
          </div>
        </div>
      );
    }
}
