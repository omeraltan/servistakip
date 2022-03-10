import { element, by, ElementFinder, ElementArrayFinder } from 'protractor';

import { waitUntilAnyDisplayed, waitUntilDisplayed, click, waitUntilHidden, isVisible } from '../../util/utils';

import NavBarPage from './../../page-objects/navbar-page';

import KlasorTemelUpdatePage from './klasor-temel-update.page-object';

const expect = chai.expect;
export class KlasorTemelDeleteDialog {
  deleteModal = element(by.className('modal'));
  private dialogTitle: ElementFinder = element(by.id('servisTakipApp.klasorTemel.delete.question'));
  private confirmButton = element(by.id('jhi-confirm-delete-klasorTemel'));

  getDialogTitle() {
    return this.dialogTitle;
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}

export default class KlasorTemelComponentsPage {
  createButton: ElementFinder = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('div table .btn-danger'));
  title: ElementFinder = element(by.id('klasor-temel-heading'));
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
    await navBarPage.getEntityPage('klasor-temel');
    await waitUntilAnyDisplayed([this.noRecords, this.table]);
    return this;
  }

  async goToCreateKlasorTemel() {
    await this.createButton.click();
    return new KlasorTemelUpdatePage();
  }

  async deleteKlasorTemel() {
    const deleteButton = this.getDeleteButton(this.records.last());
    await click(deleteButton);

    const klasorTemelDeleteDialog = new KlasorTemelDeleteDialog();
    await waitUntilDisplayed(klasorTemelDeleteDialog.deleteModal);
    expect(await klasorTemelDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/servisTakipApp.klasorTemel.delete.question/);
    await klasorTemelDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(klasorTemelDeleteDialog.deleteModal);

    expect(await isVisible(klasorTemelDeleteDialog.deleteModal)).to.be.false;
  }
}
