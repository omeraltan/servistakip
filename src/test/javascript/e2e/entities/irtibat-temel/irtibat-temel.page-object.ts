import { element, by, ElementFinder, ElementArrayFinder } from 'protractor';

import { waitUntilAnyDisplayed, waitUntilDisplayed, click, waitUntilHidden, isVisible } from '../../util/utils';

import NavBarPage from './../../page-objects/navbar-page';

import IrtibatTemelUpdatePage from './irtibat-temel-update.page-object';

const expect = chai.expect;
export class IrtibatTemelDeleteDialog {
  deleteModal = element(by.className('modal'));
  private dialogTitle: ElementFinder = element(by.id('servisTakipApp.irtibatTemel.delete.question'));
  private confirmButton = element(by.id('jhi-confirm-delete-irtibatTemel'));

  getDialogTitle() {
    return this.dialogTitle;
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}

export default class IrtibatTemelComponentsPage {
  createButton: ElementFinder = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('div table .btn-danger'));
  title: ElementFinder = element(by.id('irtibat-temel-heading'));
  noRecords: ElementFinder = element(by.css('#app-view-container .table-responsive div.alert.alert-warning'));
  table: ElementFinder = element(by.css('#app-view-container div.table-responsive > table'));

  records: ElementArrayFinder = this.table.all(by.css('tbody tr'));

  getDetailsButton(record: ElementFinder) {
    return record.element(by.css('a.btn.btn-info.btn-sm'));
  }

  getEditButton(record: ElementFinder) {
    return record.element(by.css('a.btn.btn-primary.btn-sm'));
  }

  getDeleteButton(record: ElementFinder) {
    return record.element(by.css('a.btn.btn-danger.btn-sm'));
  }

  async goToPage(navBarPage: NavBarPage) {
    await navBarPage.getEntityPage('irtibat-temel');
    await waitUntilAnyDisplayed([this.noRecords, this.table]);
    return this;
  }

  async goToCreateIrtibatTemel() {
    await this.createButton.click();
    return new IrtibatTemelUpdatePage();
  }

  async deleteIrtibatTemel() {
    const deleteButton = this.getDeleteButton(this.records.last());
    await click(deleteButton);

    const irtibatTemelDeleteDialog = new IrtibatTemelDeleteDialog();
    await waitUntilDisplayed(irtibatTemelDeleteDialog.deleteModal);
    expect(await irtibatTemelDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/servisTakipApp.irtibatTemel.delete.question/);
    await irtibatTemelDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(irtibatTemelDeleteDialog.deleteModal);

    expect(await isVisible(irtibatTemelDeleteDialog.deleteModal)).to.be.false;
  }
}
